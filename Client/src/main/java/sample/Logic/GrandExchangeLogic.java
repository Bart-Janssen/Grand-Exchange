package sample.Logic;

import com.google.gson.Gson;
import sample.Gui.IGui;
import sample.Models.MessageType;
import sample.Models.User;
import sample.Models.WebSocketMessage;
import sample.WebSocketConnection.IWebSocketConnection;
import javax.websocket.*;

@ClientEndpoint
public class GrandExchangeLogic implements IGrandExchangeLogic
{
    private IGui gui;
    private IWebSocketConnection webSocketConnection;

    public GrandExchangeLogic(IGui gui, IWebSocketConnection webSocketConnection)
    {
        this.gui = gui;
        this.webSocketConnection = webSocketConnection;
    }

    @OnOpen
    public void onWebSocketConnect()
    {
        System.out.println("[Connected]");
    }

    @OnMessage
    public void onWebSocketText(String message)
    {
        System.out.println("[Received]: " + message);
        handleServerMessage(message);
    }

    @OnClose
    public void onWebSocketClose(CloseReason reason)
    {
        System.out.println("[Closed]: " + reason);
    }

    @OnError
    public void onWebSocketError(Throwable cause)
    {
        System.out.println("[ERROR]: " + cause.getMessage());
    }

    private void handleServerMessage(String jsonMessage)
    {
        WebSocketMessage webSocketMessage;
        try
        {
            webSocketMessage = new Gson().fromJson(jsonMessage, WebSocketMessage.class);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return;
        }
        MessageType type = webSocketMessage.getOperation();
        switch (type)
        {
            case LOGIN:
                System.out.println(webSocketMessage.getMessage() + ", Logged in: " + webSocketMessage.getUser().isLoggedIn());
                break;
        }
    }

    @Override
    public void login(String username, String password)
    {
        webSocketConnection.login(new User(username, password));
    }
}