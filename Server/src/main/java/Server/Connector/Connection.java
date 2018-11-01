package Server.Connector;

import Server.Factory.ServerFactory;
import Server.Models.DatabaseServerType;
import Server.Models.WebSocketMessage;
import Server.ServerLogic.IGrandExchangeServerLogic;
import Server.SharedClientModels.MessageType;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
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
    private IGrandExchangeServerLogic logic = ServerFactory.getInstance().makeNewGrandExchangeServerLogic(DatabaseServerType.REST);

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
        }
        currentUserSession.getAsyncRemote().sendText(new Gson().toJson(messageToUser));
    }
}