package DataServer.DataServerLogic;

import DataServer.SharedServerModels.Item;
import DataServer.SharedServerModels.MarketOffer;
import DataServer.SharedServerModels.User;

import java.util.ArrayList;

public interface IDataServerLogic
{
    User login(User user);
    String register(User user);
    ArrayList<Item> getBackPackItems(int userId);
    boolean addItemToBackPack(Item item, int userId);
    boolean deleteItemFromBackPack(int itemId, int userId);
    ArrayList<MarketOffer> getMarketOffers(int userId);
    boolean sellItem(MarketOffer offer);
    boolean cancelOffer(int offerId);
    ArrayList<MarketOffer> getSearchOffers(String searchQuery, int userId);
    boolean buyItem(MarketOffer offer, int buyerId);
    int getUserCoins(int id);
    ArrayList<MarketOffer> getSellingOffers();
}