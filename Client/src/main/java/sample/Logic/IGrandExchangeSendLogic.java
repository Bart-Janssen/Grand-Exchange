package sample.Logic;

import sample.Models.Item;
import sample.Models.MarketOffer;

public interface IGrandExchangeSendLogic
{
    void login(String username, String password);
    void sellItem(MarketOffer offer);
    void calculateItemPrice(Item item);
    void getBackPackItems();
    void generateNewWeapon();
    void deleteItemFromBackPack(Item item);
    void logout();
    void getMarketOffers();
    void cancelOffer(MarketOffer offer);
}