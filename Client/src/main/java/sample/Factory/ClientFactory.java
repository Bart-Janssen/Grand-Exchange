package sample.Factory;

import sample.Gui.ILoginGui;
import sample.Logic.GrandExchangeLogic;
import sample.Logic.IGrandExchangeLogic;
import sample.Models.WebSocketType;
import sample.WebSocketConnection.WebSocketConnection;

public class ClientFactory
{
    private static ClientFactory instance = null;

    private ClientFactory(){}

    public static ClientFactory getInstance()
    {
        if (instance == null) instance = new ClientFactory();
        return instance;
    }

    public IGrandExchangeLogic makeNewIGrandExchangeLogic(ILoginGui gui, WebSocketType type)
    {
        switch (type)
        {
            case WEBSOCKETSERVER:
                return new GrandExchangeLogic(gui, new WebSocketConnection());
            default:
                return null;
        }
    }
}