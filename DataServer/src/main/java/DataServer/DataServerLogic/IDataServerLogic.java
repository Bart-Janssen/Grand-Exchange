package DataServer.DataServerLogic;

import DataServer.SharedServerModels.User;

public interface IDataServerLogic
{
    boolean login(User user);
    void register(User user);
}