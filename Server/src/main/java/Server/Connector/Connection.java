package Server.Connector;

import Server.Factory.ServerFactory;
import Server.Models.DatabaseServerType;
import Server.Models.WebSocketMessage;
import Server.ServerLogic.IGrandExchangeServerLogic;
import Server.SharedClientModels.*;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import javax.persistence.Convert;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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
        session.setMaxIdleTimeout(Long.MAX_VALUE);
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
        }
    }

    private void sellItem(WebSocketMessage webSocketMessage, Session currentUserSession)
    {
        if (sessionAndUser.get(currentUserSession).getUser().isLoggedIn())
        {
            WebSocketMessage messageToUser = new WebSocketMessage();
            messageToUser.setOperation(MessageType.SELLITEM);
            messageToUser.setUser(sessionAndUser.get(currentUserSession).getUser());
            messageToUser.setItem(webSocketMessage.getItem());
            messageToUser.setMessage("[Server] : Failed to sell item.");
            try
            {
                if (logic.sellItem(new MarketOffer(Integer.parseInt(messageToUser.getMessage()), messageToUser.getItem(), MarketOfferType.SELL, sessionAndUser.get(currentUserSession).getUser())))
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
            int price = logic.calculateItemPrice(sessionAndUser.get(currentUserSession).getUser(), webSocketMessage.getItem());
            webSocketMessage.getItem().setPrice(price);
            messageToUser.setMessage(new Gson().toJson(new MarketOffer(price, webSocketMessage.getItem(), MarketOfferType.SELL, null)));
            if (price == -1) messageToUser.setMessage("Cannot sell item, item needs to be repaired first.");
            messageToUser.setItem(webSocketMessage.getItem());
            currentUserSession.getAsyncRemote().sendText(new Gson().toJson(messageToUser));
        }
    }

    private void login(WebSocketMessage webSocketMessage, Session currentUserSession)
    {
        WebSocketMessage messageToUser = new WebSocketMessage();
        messageToUser.setOperation(MessageType.LOGIN);
        messageToUser.setUser(webSocketMessage.getUser());
        messageToUser.setMessage("[Server] : Failed to login!");
        if (logic.login(messageToUser.getUser()))
        {
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