package Server.ServerLogic;

import Server.DataServer.IGrandExchangeDatabaseServer;
import Server.SharedClientModels.User;

public class GrandExchangeServerLogic implements IGrandExchangeServerLogic
{
    private IGrandExchangeDatabaseServer databaseServer;

    public GrandExchangeServerLogic(IGrandExchangeDatabaseServer databaseServer)
    {
        this.databaseServer = databaseServer;
    }

    @Override
    public boolean login(User user)
    {
        return databaseServer.login(user);
    }
}