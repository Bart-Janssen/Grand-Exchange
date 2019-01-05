package sample.WebSocketConnection;

import sample.Models.Item;
import sample.Models.MarketOffer;
import sample.Models.User;

import javax.websocket.Session;

public interface IConnection
{
    void login(User message);
    void sellItem(MarketOffer offer);
    void calculateItemPrice(Item item);
    void getBackPackItems();
    void generateNewWeapon();
    void sentHeartBeat();
    void deleteItemFromBackPack(Item item);
    void logout();
    void getMarketOffers();
    void cancelOffer(MarketOffer offer);
    void getMarketOffersCount();
    void getSearchOffers(String searchQuery);
    void buyItem(MarketOffer offer);
}