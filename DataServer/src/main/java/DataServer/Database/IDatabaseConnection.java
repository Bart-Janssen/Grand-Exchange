package DataServer.Database;

import DataServer.SharedServerModels.User;

public interface IDatabaseConnection
{
    boolean login(User user);
    void register(User user);
}