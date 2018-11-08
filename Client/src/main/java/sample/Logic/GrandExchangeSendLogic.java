package sample.Logic;

import com.google.gson.Gson;
import javafx.application.Platform;
import sample.Gui.ILoginGui;
import sample.Models.*;
import sample.WebSocketConnection.IWebSocketConnection;
import javax.websocket.*;

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