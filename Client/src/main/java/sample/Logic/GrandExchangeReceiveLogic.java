package sample.Logic;

import com.google.gson.Gson;
import javafx.application.Platform;
import sample.Gui.Gui;
import sample.Gui.IGameGui;
import sample.Gui.ILoginGui;
import sample.Gui.IMarketGui;
import sample.Models.*;
import javax.websocket.*;

@ClientEndpoint
public class GrandExchangeReceiveLogic implements IGrandExchangeReceiveLogic
{
    private Gui gui;
    private static GrandExchangeReceiveLogic instance = null;

    public static GrandExchangeReceiveLogic getInstance()
    {
        if (instance == null) instance = new GrandExchangeReceiveLogic();
        return instance;
    }

    private GrandExchangeReceiveLogic(){}

    @Override
    public void setGui(Gui gui)
    {
        this.gui = gui;
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
                Platform.runLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        ((IGameGui)gui).MBOX(webSocketMessage.getMessage());
                    }
                });
                break;
        }
    }

    private void login(WebSocketMessage webSocketMessage)
    {
        if (webSocketMessage.getUser().isLoggedIn())
        {
            Platform.runLater(new Runnable()
            {
                @Override
                public void run()
                {
                    ((ILoginGui)gui).callGameGui();
                }
            });
            return;
        }
        ((ILoginGui)gui).loginFailed();
    }
}