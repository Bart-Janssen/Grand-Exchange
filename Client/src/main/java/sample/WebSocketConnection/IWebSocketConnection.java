package sample.WebSocketConnection;

import sample.Models.Item;
import sample.Models.User;

import javax.websocket.Session;

public interface IWebSocketConnection
{
    void login(User message);
    void sellItem(int price, Item item);
    void calculateItemPrice(Item item);
}