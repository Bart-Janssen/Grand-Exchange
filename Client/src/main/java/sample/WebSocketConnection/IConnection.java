package sample.WebSocketConnection;

import sample.Models.Item;
import sample.Models.User;

import javax.websocket.Session;

public interface IConnection
{
    void login(User message);
    void sellItem(int price, Item item);
    void calculateItemPrice(Item item);
    void getBackPackItems();
    void generateNewWeapon();
    void sentHeartBeat();
    void deleteItemFromBackPack(Item item);
}