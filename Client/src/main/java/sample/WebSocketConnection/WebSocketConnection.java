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
        UserSession.getInstance().getSession().getAsyncRemote().sendText(new Gson().toJson(webSocketMessage));
    }

    @Override
    public void sellItem(int price, User user, Item item)
    {
        WebSocketMessage webSocketMessage = new WebSocketMessage();
        webSocketMessage.setItem(item);
        webSocketMessage.setUser(user);
        webSocketMessage.setMessage(Integer.toString(price));
        webSocketMessage.setOperation(MessageType.SELLITEM);
        UserSession.getInstance().getSession().getAsyncRemote().sendText(new Gson().toJson(webSocketMessage));
    }

    @Override
    public void calculateItemPrice(User user, Item item)
    {
        WebSocketMessage webSocketMessage = new WebSocketMessage();
        webSocketMessage.setItem(item);
        webSocketMessage.setUser(user);
        webSocketMessage.setOperation(MessageType.CALCULATEITEMPRICE);
        UserSession.getInstance().getSession().getAsyncRemote().sendText(new Gson().toJson(webSocketMessage));
    }
}