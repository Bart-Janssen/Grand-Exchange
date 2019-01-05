package DataServer.Database;

import DataServer.SharedServerModels.Item;
import DataServer.SharedServerModels.MarketOffer;
import DataServer.SharedServerModels.User;
import java.util.ArrayList;

public interface IDatabaseConnection
{
    User login(User user);
    void register(User user);
    ArrayList<Item> getBackPackItems(int userId);
    boolean addItemToBackPack(Item item, int userId);
    boolean deleteItemFromBackPack(int itemId, int userId);
    ArrayList<MarketOffer> getMarketOffers(int userId);
    boolean sellItem(MarketOffer offer);
    boolean cancelOffer(int offerId);
    ArrayList<MarketOffer> getSearchOffers(String searchQuery, int userId);
    boolean buyItem(MarketOffer offer, int buyerId);

    ArrayList<User> TEST(int id);//TODO:
    ArrayList<MarketOffer> getSellingOffers();
}