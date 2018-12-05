package sample.WebSocketConnection;

import com.google.gson.Gson;
import sample.Models.*;

public class Connection implements IConnection
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
    public void sellItem(int price, Item item)
    {
        WebSocketMessage webSocketMessage = new WebSocketMessage();
        webSocketMessage.addItem(item);
        webSocketMessage.setMessage(Integer.toString(price));
        webSocketMessage.setOperation(MessageType.SELLITEM);
        UserSession.getInstance().getSession().getAsyncRemote().sendText(new Gson().toJson(webSocketMessage));
    }

    @Override
    public void calculateItemPrice(Item item)
    {
        WebSocketMessage webSocketMessage = new WebSocketMessage();
        webSocketMessage.addItem(item);
        webSocketMessage.setOperation(MessageType.CALCULATEITEMPRICE);
        UserSession.getInstance().getSession().getAsyncRemote().sendText(new Gson().toJson(webSocketMessage));
    }

    @Override
    public void getBackPackItems()
    {
        WebSocketMessage webSocketMessage = new WebSocketMessage();
        webSocketMessage.setOperation(MessageType.GET_BACKPACK_ITEMS);
        UserSession.getInstance().getSession().getAsyncRemote().sendText(new Gson().toJson(webSocketMessage));
    }

    @Override
    public void generateNewWeapon()
    {
        WebSocketMessage webSocketMessage = new WebSocketMessage();
        webSocketMessage.setOperation(MessageType.GENERATE_NEW_WEAPON);
        UserSession.getInstance().getSession().getAsyncRemote().sendText(new Gson().toJson(webSocketMessage));
    }

    @Override
    public void sentHeartBeat()
    {
        System.out.println("Heart beat sent.");
        WebSocketMessage webSocketMessage = new WebSocketMessage();
        webSocketMessage.setOperation(MessageType.HEARTBEAT);
        UserSession.getInstance().getSession().getAsyncRemote().sendText(new Gson().toJson(webSocketMessage));
    }
}