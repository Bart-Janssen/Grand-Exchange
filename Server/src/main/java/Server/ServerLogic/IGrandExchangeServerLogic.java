package Server.ServerLogic;

import Server.Models.WebSocketMessage;
import Server.SharedClientModels.Item;
import Server.SharedClientModels.User;

public interface IGrandExchangeServerLogic
{
    boolean login(User user);
    WebSocketMessage sellItem(User user, Item item);
}