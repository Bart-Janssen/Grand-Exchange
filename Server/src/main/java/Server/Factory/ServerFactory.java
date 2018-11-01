package Server.Factory;

import Server.Models.DatabaseServer;
import Server.ServerLogic.GrandExchangeServerLogic;
import Server.ServerLogic.IGrandExchangeServerLogic;

public class ServerFactory
{
    private static ServerFactory instance = null;

    private ServerFactory(){}

    public static ServerFactory getInstance()
    {
        if (instance == null) instance = new ServerFactory();
        return instance;
    }

    public IGrandExchangeServerLogic makeNewGrandExchangeServerLogic(DatabaseServer server)
    {
        switch (server)
        {
            case MYSQL:
                return new GrandExchangeServerLogic();
            default:
                return null;
        }
    }
}