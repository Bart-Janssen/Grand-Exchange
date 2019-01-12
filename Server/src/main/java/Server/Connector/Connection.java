package Server.Connector;

import Server.Factory.ServerFactory;
import Server.Models.DatabaseServerType;
import Server.Models.Logger;
import Server.Models.WebSocketMessage;
import Server.ServerLogic.IGrandExchangeServerLogic;
import Server.SharedClientModels.*;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import javafx.collections.FXCollections;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@ServerEndpoint(value="/grandExchangeServer/")
public class Connection
{
    private IGrandExchangeServerLogic logic = ServerFactory.getInstance().makeNewGrandExchangeServerLogic(DatabaseServerType.REST);//TODO: database server to rest

    private static HashMap<Session, WebSocketMessage> sessionAndUser = new HashMap<>();
    private static ArrayList<WebSocketMessage> pendingSells = new ArrayList<>();

    @OnOpen
    public void onConnect(Session session)
    {
        new Logger().print("[WebSocket Connected] SessionID: " + session.getId());
        session.setMaxIdleTimeout(300000);
        sessionAndUser.put(session, null);
    }

    @OnMessage
    public void onText(String message, Session session)
    {
        handleMessageFromClient(message, session);
    }

    @OnClose
    public void onClose(CloseReason reason, Session session)
    {
        new Logger().print("[WebSocket Session ID] : " + session.getId() + " [Socket Closed]: " + reason);
        try
        {
            session.close();
            sessionAndUser.remove(session);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static <T, E> Set<T> getKeysByValue(Map<T, E> map)
    {
        return map.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toSet());
    }

    @OnError
    public void onError(Throwable cause, Session session)
    {
        new Logger().print("[WebSocket Session ID] : " + session.getId() + "[ERROR]: ");
        cause.printStackTrace(System.err);
    }

    private void handleMessageFromClient(String jsonMessage, Session currentUserSession)
    {
        WebSocketMessage webSocketMessage;
        try
        {
            webSocketMessage = new Gson().fromJson(jsonMessage, WebSocketMessage.class);
        }
        catch (JsonSyntaxException ex)
        {
            new Logger().print("[WebSocket ERROR: cannot parse Json message " + jsonMessage);
            return;
        }
        MessageType operation = webSocketMessage.getOperation();
        switch (operation)
        {
            case LOGIN:
                login(webSocketMessage, currentUserSession);
                break;
            case REGISTER:
                register(webSocketMessage, currentUserSession);
                break;
            case LOGOUT:
                logout(currentUserSession);
                break;
            case SELL_ITEM:
                sellItem(webSocketMessage, currentUserSession);
                break;
            case BUY_ITEM:
                buyItem(webSocketMessage, currentUserSession);
                break;
            case CANCEL_OFFER:
                cancelOffer(webSocketMessage, currentUserSession);
                break;
            case CALCULATE_ITEM_PRICE:
                calculateItemPrice(webSocketMessage, currentUserSession);
                break;
            case GET_BACKPACK_ITEMS:
                getBackPackItems(currentUserSession);
                break;
            case GET_MARKET_OFFERS:
                getMarketOffers(currentUserSession);
                break;
            case GENERATE_NEW_WEAPON:
                generateNewWeapon(currentUserSession);
                break;
            case DELETE_ITEM_FROM_BACKPACK:
                deleteItemFromBackPack(webSocketMessage, currentUserSession);
                break;
            case HEARTBEAT:
                new Logger().print("[Heartbeat] : " + currentUserSession.getId() + " is still alive.");
                break;
            case GET_MARKET_OFFERS_COUNT:
                getMarketOfferCount(currentUserSession);
                break;
            case GET_SEARCH_OFFERS:
                getSearchOffers(webSocketMessage, currentUserSession);
                break;
                default:
                    break;
        }
    }

    private int getUserCoins(int id)
    {
        return logic.getUserCoins(id);
    }

    private void buyItem(WebSocketMessage webSocketMessage, Session currentUserSession)
    {
        if (sessionAndUser.get(currentUserSession).getUser().isLoggedIn())
        {
            WebSocketMessage messageToUser = new WebSocketMessage();
            messageToUser.setOperation(MessageType.BUY_ITEM);
            messageToUser.setMessage("You don't have enough coins.");
            if (sessionAndUser.get(currentUserSession).getUser().getCoins() >= webSocketMessage.getOffers().get(0).getPrice())
            {
                if (logic.buyItem(webSocketMessage.getOffers().get(0), sessionAndUser.get(currentUserSession).getUser().getId()))
                {
                    sessionAndUser.get(currentUserSession).getUser().setCoins(getUserCoins(sessionAndUser.get(currentUserSession).getUser().getId()));
                    messageToUser.setMessage("Successfully bought " + webSocketMessage.getOffers().get(0).getItem().getName() + ", it's added to your inventory.");
                    messageToUser.setUser(sessionAndUser.get(currentUserSession).getUser());
                }
            }
            currentUserSession.getAsyncRemote().sendText(new Gson().toJson(messageToUser));
            sentSoldToSeller(webSocketMessage, currentUserSession);
        }
    }

    private void sentSoldToSeller(WebSocketMessage webSocketMessage, Session currentUserSession)
    {
        WebSocketMessage messageToUser = new WebSocketMessage();
        messageToUser.setOperation(MessageType.SOLD);
        messageToUser.addItem(webSocketMessage.getOffers().get(0).getItem());
        messageToUser.setMessage("One of your items has been sold!");
        for (Session sellerSession : getKeysByValue(sessionAndUser))
        {
            if (sessionAndUser.get(sellerSession).getUser().getId() == webSocketMessage.getOffers().get(0).getUserId())
            {
                sessionAndUser.get(sellerSession).getUser().setCoins(getUserCoins(sessionAndUser.get(sellerSession).getUser().getId()));
                messageToUser.setUser(sessionAndUser.get(sellerSession).getUser());
                sellerSession.getAsyncRemote().sendText(new Gson().toJson(messageToUser));
                return;
            }
        }
        messageToUser.addMarketOffer(webSocketMessage.getOffers().get(0));
        messageToUser.setUser(sessionAndUser.get(currentUserSession).getUser());
        pendingSells.add(messageToUser);
        new Logger().print("Seller is not online!");
    }

    private void getSearchOffers(WebSocketMessage webSocketMessage, Session currentUserSession)
    {
        if (sessionAndUser.get(currentUserSession).getUser().isLoggedIn())
        {
            WebSocketMessage messageToUser = new WebSocketMessage();
            messageToUser.setMarketOffers(logic.getSearchOffers(webSocketMessage.getMessage(), sessionAndUser.get(currentUserSession).getUser().getId()));
            messageToUser.setOperation(MessageType.GET_SEARCH_OFFERS);
            currentUserSession.getAsyncRemote().sendText(new Gson().toJson(messageToUser));
        }
    }

    private void logout(Session currentUserSession)
    {
        if (sessionAndUser.get(currentUserSession).getUser().isLoggedIn()) sessionAndUser.get(currentUserSession).getUser().setLoggedIn(false);
    }

    private void getMarketOfferCount(Session currentUserSession)
    {
        if (sessionAndUser.get(currentUserSession).getUser().isLoggedIn())
        {
            WebSocketMessage messageToUser = new WebSocketMessage();
            messageToUser.setMessage(Integer.toString(logic.getMarketOffers(sessionAndUser.get(currentUserSession).getUser().getId()).size()));
            messageToUser.setOperation(MessageType.GET_MARKET_OFFERS_COUNT);
            currentUserSession.getAsyncRemote().sendText(new Gson().toJson(messageToUser));
        }
    }

    private void deleteItemFromBackPack(WebSocketMessage webSocketMessage, Session currentUserSession)
    {
        if (sessionAndUser.get(currentUserSession).getUser().isLoggedIn())
        {
            boolean deleted = logic.deleteItemFromBackPack(webSocketMessage.getItems().get(0), sessionAndUser.get(currentUserSession).getUser().getId());
            WebSocketMessage messageToUser = new WebSocketMessage();
            messageToUser.setOperation(MessageType.DELETE_ITEM_FROM_BACKPACK);
            messageToUser.setMessage(deleted ? "Successfully destroyed item." : "Failed to destroyed item.");
            currentUserSession.getAsyncRemote().sendText(new Gson().toJson(messageToUser));
        }
    }

    private void generateNewWeapon(Session currentUserSession)
    {
        if (sessionAndUser.get(currentUserSession).getUser().isLoggedIn())
        {
            WebSocketMessage messageToUser = new WebSocketMessage();
            messageToUser.setOperation(MessageType.GENERATE_NEW_WEAPON);
            messageToUser.setItems(logic.generateItem(sessionAndUser.get(currentUserSession).getUser().getId()));
            currentUserSession.getAsyncRemote().sendText(new Gson().toJson(messageToUser));
        }
    }

    private void getBackPackItems(Session currentUserSession)
    {
        if (sessionAndUser.get(currentUserSession).getUser().isLoggedIn())
        {
            ArrayList<Item> items = logic.getBackPackItems(sessionAndUser.get(currentUserSession).getUser().getId());
            WebSocketMessage messageToUser = new WebSocketMessage();
            messageToUser.setOperation(MessageType.GET_BACKPACK_ITEMS);
            messageToUser.setItems(items);
            currentUserSession.getAsyncRemote().sendText(new Gson().toJson(messageToUser));
        }
    }

    private void getMarketOffers(Session currentUserSession)
    {
        if (sessionAndUser.get(currentUserSession).getUser().isLoggedIn())
        {
            ArrayList<MarketOffer> offers = logic.getMarketOffers(sessionAndUser.get(currentUserSession).getUser().getId());
            WebSocketMessage messageToUser = new WebSocketMessage();
            messageToUser.setOperation(MessageType.GET_MARKET_OFFERS);
            messageToUser.setMarketOffers(offers);
            currentUserSession.getAsyncRemote().sendText(new Gson().toJson(messageToUser));
        }
    }

    private void sellItem(WebSocketMessage webSocketMessage, Session currentUserSession)
    {
        if (sessionAndUser.get(currentUserSession).getUser().isLoggedIn())
        {
            WebSocketMessage messageToUser = new WebSocketMessage();
            messageToUser.setOperation(MessageType.SELL_ITEM);
            messageToUser.setMessage("Failed to offer item.");
            MarketOffer newOffer = webSocketMessage.getOffers().get(0);
            newOffer.setUserId(sessionAndUser.get(currentUserSession).getUser().getId());
            if (logic.sellItem(newOffer)) messageToUser.setMessage("Successfully offered item to market.");
            currentUserSession.getAsyncRemote().sendText(new Gson().toJson(messageToUser));
            messageToUser.setOperation(MessageType.NEW_OFFER_PLACED_ON_MARKET);
            messageToUser.setMessage("There has placed a new " + webSocketMessage.getOffers().get(0).getItem().getName() + " on the market.");
            for (Session session : getKeysByValue(sessionAndUser))
            {
                if (session != currentUserSession) session.getAsyncRemote().sendText(new Gson().toJson(messageToUser));
            }
        }
    }

    private void cancelOffer(WebSocketMessage webSocketMessage, Session currentUserSession)
    {
        if (sessionAndUser.get(currentUserSession).getUser().isLoggedIn())
        {
            WebSocketMessage messageToUser = new WebSocketMessage();
            messageToUser.setOperation(MessageType.CANCEL_OFFER);
            messageToUser.setMessage("Failed to cancel offer.");
            if (logic.cancelOffer(webSocketMessage.getOffers().get(0))) messageToUser.setMessage("Successfully canceled offer.");
            currentUserSession.getAsyncRemote().sendText(new Gson().toJson(messageToUser));
            for (WebSocketMessage pendingWebSocketMessage : FXCollections.observableArrayList(pendingSells))
            {
                if (pendingWebSocketMessage.getOffers().get(0).getUserId() == sessionAndUser.get(currentUserSession).getUser().getId()) pendingSells.remove(pendingWebSocketMessage);
            }
        }
    }

    private void calculateItemPrice(WebSocketMessage webSocketMessage, Session currentUserSession)
    {
        if (sessionAndUser.get(currentUserSession).getUser().isLoggedIn())
        {
            WebSocketMessage messageToUser = new WebSocketMessage();
            messageToUser.setOperation(MessageType.CALCULATE_ITEM_PRICE);
            messageToUser.setUser(sessionAndUser.get(currentUserSession).getUser());
            int price = logic.calculateItemPrice(sessionAndUser.get(currentUserSession).getUser(), webSocketMessage.getItems().get(0));
            webSocketMessage.getItems().get(0).setPrice(price);
            messageToUser.addMarketOffer(new MarketOffer(-1, -1, price, webSocketMessage.getItems().get(0), MarketOfferType.SELL));
            if (price == -1) messageToUser.setMessage("Cannot sell item, item needs to be repaired first.");
            messageToUser.addItem(webSocketMessage.getItems().get(0));
            currentUserSession.getAsyncRemote().sendText(new Gson().toJson(messageToUser));
        }
    }

    private void login(WebSocketMessage webSocketMessage, Session currentUserSession)
    {
        WebSocketMessage messageToUser = new WebSocketMessage();
        messageToUser.setOperation(MessageType.LOGIN);
        messageToUser.setMessage("Failed to login!");
        User user = logic.login(webSocketMessage.getUser());
        if (user != null)
        {
            webSocketMessage.setUser(user);
            messageToUser.setUser(user);
            messageToUser.setMessage("Welcome to the 'Game'!");
            sessionAndUser.put(currentUserSession, webSocketMessage);
            sessionAndUser.get(currentUserSession).getUser().setLoggedIn(true);
        }
        currentUserSession.getAsyncRemote().sendText(new Gson().toJson(messageToUser));
        checkPendingSells(currentUserSession);
    }

    private void checkPendingSells(Session currentUserSession)
    {
        for (WebSocketMessage webSocketMessage : pendingSells)
        {
            if (sessionAndUser.get(currentUserSession).getUser().getId() == webSocketMessage.getOffers().get(0).getUserId()) currentUserSession.getAsyncRemote().sendText(new Gson().toJson(webSocketMessage));
        }
    }

    private void register(WebSocketMessage webSocketMessage, Session currentUserSession)
    {
        WebSocketMessage messageToUser = new WebSocketMessage();
        String message = logic.register(webSocketMessage.getUser());
        messageToUser.setOperation(MessageType.REGISTER);
        messageToUser.setMessage(message);
        currentUserSession.getAsyncRemote().sendText(new Gson().toJson(messageToUser));
    }
}