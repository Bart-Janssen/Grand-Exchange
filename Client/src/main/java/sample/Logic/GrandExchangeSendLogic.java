package sample.Logic;

import sample.Models.*;
import sample.WebSocketConnection.IWebSocketConnection;

public class GrandExchangeSendLogic implements IGrandExchangeSendLogic
{
    private IWebSocketConnection webSocketConnection;

    public GrandExchangeSendLogic(IWebSocketConnection webSocketConnection)
    {
        this.webSocketConnection = webSocketConnection;
    }

    @Override
    public void login(String username, String password)
    {
        webSocketConnection.login(new User(username, password));
    }

    @Override
    public void sellItem(Item item)
    {
        webSocketConnection.sellItem(item);
    }
}