package sample.Factory;

import sample.Logic.*;
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

    public ICalculateLogic makeNewCalculateLogic()
    {
        return new CalculateLogic();
    }
}