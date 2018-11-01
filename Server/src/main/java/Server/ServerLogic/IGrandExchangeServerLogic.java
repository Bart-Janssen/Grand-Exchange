package Server.ServerLogic;

import Server.SharedClientModels.User;

public interface IGrandExchangeServerLogic
{
    boolean login(User user);
}