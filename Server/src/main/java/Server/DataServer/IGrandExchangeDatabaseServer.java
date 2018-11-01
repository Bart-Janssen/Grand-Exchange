package Server.DataServer;

import Server.SharedClientModels.User;

public interface IGrandExchangeDatabaseServer
{
    boolean login(User user);
    void register(User user);
}