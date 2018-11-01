package Server.Factory;

import Server.DataServer.GrandExchangeDatabaseServer;
import Server.Models.DatabaseServerType;
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

    public IGrandExchangeServerLogic makeNewGrandExchangeServerLogic(DatabaseServerType server)
    {
        switch (server)
        {
            case REST:
                return new GrandExchangeServerLogic(new GrandExchangeDatabaseServer());
            default:
                return null;
        }
    }
}