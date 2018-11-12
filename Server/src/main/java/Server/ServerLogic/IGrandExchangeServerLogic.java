package Server.ServerLogic;

import Server.Models.WebSocketMessage;
import Server.SharedClientModels.Item;
import Server.SharedClientModels.User;

public interface IGrandExchangeServerLogic
{
    boolean login(User user);
    boolean sellItem(int price, User user, Item item);
    int calculateItemPrice(User user, Item item);
}