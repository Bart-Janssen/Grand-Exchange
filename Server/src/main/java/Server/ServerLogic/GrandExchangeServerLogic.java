package Server.ServerLogic;

import Server.SharedClientModels.User;

public class GrandExchangeServerLogic implements IGrandExchangeServerLogic
{
    @Override
    public void login(User user)
    {
        System.out.println("Logged in: " + message);
    }
}