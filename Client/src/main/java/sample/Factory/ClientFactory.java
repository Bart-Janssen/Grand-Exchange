package sample.Factory;

import sample.Logic.GrandExchangeReceiveLogic;
import sample.Logic.GrandExchangeSendLogic;
import sample.Logic.IGrandExchangeReceiveLogic;
import sample.Logic.IGrandExchangeSendLogic;
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

    public IGrandExchangeReceiveLogic makeNewGrandExchangeReceiveLogic(WebSocketType type)
    {
        switch (type)
        {
            case WEBSOCKETSERVER:
                return GrandExchangeReceiveLogic.getInstance();
            default:
                return null;
        }
    }

    public IGrandExchangeSendLogic makeNewGrandExchangeSendLogic(WebSocketType type)
    {
        switch (type)
        {
            case WEBSOCKETSERVER:
                return new GrandExchangeSendLogic(new WebSocketConnection());
            default:
                return null;
        }
    }
}