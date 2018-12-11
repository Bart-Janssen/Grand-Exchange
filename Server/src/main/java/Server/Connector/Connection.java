package Server.Connector;

import Server.Factory.ServerFactory;
import Server.Models.DatabaseServerType;
import Server.Models.WebSocketMessage;
import Server.ServerLogic.IGrandExchangeServerLogic;
import Server.SharedClientModels.*;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.persistence.Convert;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@ServerEndpoint(value="/grandExchangeServer/")
public class Connection
{
    private IGrandExchangeServerLogic logic = ServerFactory.getInstance().makeNewGrandExchangeServerLogic(DatabaseServerType.REST);//TODO: database server to rest

    private HashMap<Session, WebSocketMessage> sessionAndUser = new HashMap<>();

    @OnOpen
    public void onConnect(Session session)
    {
        System.out.println("[WebSocket Connected] SessionID: " + session.getId());
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
        System.out.println("[WebSocket Session ID] : " + session.getId() + " [Socket Closed]: " + reason);
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
        System.out.println("[WebSocket Session ID] : " + session.getId() + "[ERROR]: ");
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
            System.out.println("[WebSocket ERROR: cannot parse Json message " + jsonMessage);
            return;
        }
        MessageType operation = webSocketMessage.getOperation();
        switch (operation)
        {
            case LOGIN:
                login(webSocketMessage, currentUserSession);
                break;
            case SELLITEM:
                sellItem(webSocketMessage, currentUserSession);
                break;
            case CALCULATEITEMPRICE:
                calculateItemPrice(webSocketMessage, currentUserSession);
                break;
            case GET_BACKPACK_ITEMS:
                getBackPackItems(currentUserSession);
                break;
            case GENERATE_NEW_WEAPON:
                generateNewWeapon(currentUserSession);
                break;
            case DELETE_ITEM_FROM_BACKPACK:
                deleteItemFromBackPack(webSocketMessage, currentUserSession);
                break;
            case HEARTBEAT:
                System.out.println("[Heartbeat] : " + currentUserSession.getId() + "");
                break;
        }
    }

    private void deleteItemFromBackPack(WebSocketMessage webSocketMessage, Session currentUserSession)
    {
        if (sessionAndUser.get(currentUserSession).getUser().isLoggedIn())
        {
            boolean deleted = logic.deleteItemFromBackPack(webSocketMessage.getItems().get(0), sessionAndUser.get(currentUserSession).getUser().getId());
            WebSocketMessage messageToUser = new WebSocketMessage();
            messageToUser.setOperation(MessageType.DELETE_ITEM_FROM_BACKPACK);
            messageToUser.setMessage(Boolean.toString(deleted));
            currentUserSession.getAsyncRemote().sendText(new Gson().toJson(messageToUser));
        }
    }

    private void generateNewWeapon(Session currentUserSession)
    {
        if (sessionAndUser.get(currentUserSession).getUser().isLoggedIn())
        {
            WebSocketMessage messageToUser = new WebSocketMessage();
            messageToUser.setOperation(MessageType.GENERATE_NEW_WEAPON);
            messageToUser.setItems(logic.generateNewWeapon(sessionAndUser.get(currentUserSession).getUser().getId()));
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

    private void sellItem(WebSocketMessage webSocketMessage, Session currentUserSession)
    {
        if (sessionAndUser.get(currentUserSession).getUser().isLoggedIn())
        {
            WebSocketMessage messageToUser = new WebSocketMessage();
            messageToUser.setOperation(MessageType.SELLITEM);
            messageToUser.setUser(sessionAndUser.get(currentUserSession).getUser());
            messageToUser.addItem(webSocketMessage.getItems().get(0));
            messageToUser.setMessage("[Server] : Failed to sell item.");
            try
            {
                if (logic.sellItem(new MarketOffer(Integer.parseInt(messageToUser.getMessage()), messageToUser.getItems().get(0), MarketOfferType.SELL, sessionAndUser.get(currentUserSession).getUser())))
                {
                    messageToUser.setMessage("[Server] : Successfully sold item.");
                }
            }
            catch (Exception ex)
            {
                messageToUser.setMessage("[Server] : Error in message.");
                currentUserSession.getAsyncRemote().sendText(new Gson().toJson(messageToUser));
                return;
            }
            currentUserSession.getAsyncRemote().sendText(new Gson().toJson(messageToUser));
        }
    }

    private void calculateItemPrice(WebSocketMessage webSocketMessage, Session currentUserSession)
    {
        if (sessionAndUser.get(currentUserSession).getUser().isLoggedIn())
        {
            WebSocketMessage messageToUser = new WebSocketMessage();
            messageToUser.setOperation(MessageType.CALCULATEITEMPRICE);
            messageToUser.setUser(sessionAndUser.get(currentUserSession).getUser());
            int price = logic.calculateItemPrice(sessionAndUser.get(currentUserSession).getUser(), webSocketMessage.getItems().get(0));
            webSocketMessage.getItems().get(0).setPrice(price);
            messageToUser.setMessage(new Gson().toJson(new MarketOffer(price, webSocketMessage.getItems().get(0), MarketOfferType.SELL, null)));
            if (price == -1) messageToUser.setMessage("Cannot sell item, item needs to be repaired first.");
            messageToUser.addItem(webSocketMessage.getItems().get(0));
            currentUserSession.getAsyncRemote().sendText(new Gson().toJson(messageToUser));
            return;
        }
        System.out.println("User is not logged in!");
    }

    private void login(WebSocketMessage webSocketMessage, Session currentUserSession)
    {
        WebSocketMessage messageToUser = new WebSocketMessage();
        messageToUser.setOperation(MessageType.LOGIN);
        messageToUser.setMessage("[Server] : Failed to login!");
        User user = logic.login(webSocketMessage.getUser());
        if (user != null)
        {
            webSocketMessage.setUser(user);
            messageToUser.setUser(user);
            messageToUser.setMessage("[Server] : Successfully logged in!");
            sessionAndUser.put(currentUserSession, webSocketMessage);//TODO naar register
            sessionAndUser.get(currentUserSession).getUser().setLoggedIn(true);
        }
        currentUserSession.getAsyncRemote().sendText(new Gson().toJson(messageToUser));
    }

    private void register()
    {
        //TODO: this
    }
}