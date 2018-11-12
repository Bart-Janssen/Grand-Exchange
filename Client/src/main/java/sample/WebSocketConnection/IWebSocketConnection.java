package sample.WebSocketConnection;

import sample.Models.Item;
import sample.Models.User;

import javax.websocket.Session;

public interface IWebSocketConnection
{
    void login(User message);
    void sellItem(int price, User user, Item item);
    void calculateItemPrice(User user, Item item);
}