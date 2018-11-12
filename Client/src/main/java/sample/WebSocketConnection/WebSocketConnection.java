package sample.WebSocketConnection;

import com.google.gson.Gson;
import sample.Models.*;

public class WebSocketConnection implements IWebSocketConnection
{
    @Override
    public void login(User user)
    {
        WebSocketMessage webSocketMessage = new WebSocketMessage();
        webSocketMessage.setUser(user);
        webSocketMessage.setOperation(MessageType.LOGIN);
        String jsonMessage = new Gson().toJson(webSocketMessage);
        UserSession.getInstance().getSession().getAsyncRemote().sendText(jsonMessage);
    }

    @Override
    public void sellItem(Item item)
    {
        WebSocketMessage webSocketMessage = new WebSocketMessage();
        webSocketMessage.setItem(item);
        webSocketMessage.setOperation(MessageType.SELLITEM);
        String jsonMessage = new Gson().toJson(webSocketMessage);
        UserSession.getInstance().getSession().getAsyncRemote().sendText(jsonMessage);
    }
}