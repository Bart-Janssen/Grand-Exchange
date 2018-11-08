package sample.WebSocketConnection;

import sample.Models.Item;
import sample.Models.User;

public interface IWebSocketConnection
{
    void login(User message);
    void sellItem(Item item);
}