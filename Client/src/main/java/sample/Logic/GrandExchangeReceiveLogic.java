package sample.Logic;

import com.google.gson.Gson;
import javafx.application.Platform;
import sample.Gui.*;
import sample.Models.*;
import javax.websocket.*;

@ClientEndpoint
public class GrandExchangeReceiveLogic implements IGrandExchangeReceiveLogic
{
    private Controller controller;
    private static GrandExchangeReceiveLogic instance = null;

    public static GrandExchangeReceiveLogic getInstance()
    {
        if (instance == null) instance = new GrandExchangeReceiveLogic();
        return instance;
    }

    private GrandExchangeReceiveLogic(){}

    @Override
    public void setController(Controller controller)
    {
        this.controller = controller;
    }

    @OnOpen
    public void onWebSocketConnect()
    {
        System.out.println("[Connected to server]");
    }

    @OnMessage
    public void onWebSocketText(String message)
    {
        handleServerMessage(message);
    }

    @OnClose
    public void onWebSocketClose(CloseReason reason)
    {
        System.out.println("[Closed] : " + reason);
    }

    @OnError
    public void onWebSocketError(Throwable cause)
    {
        System.out.println("[ERROR] : " + cause.getMessage());
    }

    private void handleServerMessage(String jsonMessage)
    {
        final WebSocketMessage webSocketMessage;
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
                login(webSocketMessage);
                break;
            case SELLITEM:
                ((IGameGui)controller).MBOX(webSocketMessage.getMessage());
                break;
            case CALCULATEITEMPRICE:
                ((IBackPackGui)controller).switchToMarketController();
                Platform.runLater(() -> ((IMarketGui)controller).showCalculatedPrice(webSocketMessage.getMessage()));
                break;
        }
    }

    private void login(WebSocketMessage webSocketMessage)
    {
        System.out.println(webSocketMessage.getMessage());
        if (webSocketMessage.getUser().isLoggedIn())
        {
            ((ILoginGui)controller).callGameGui();
            return;
        }
        ((ILoginGui)controller).loginFailed();
    }
}