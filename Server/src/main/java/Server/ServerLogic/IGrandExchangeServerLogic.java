package Server.ServerLogic;

import Server.SharedClientModels.Item;
import Server.SharedClientModels.User;

public interface IGrandExchangeServerLogic
{
    boolean login(User user);
    boolean sellItem(Item item);
}