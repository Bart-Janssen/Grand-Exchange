package Server.DataServer;

import Server.SharedClientModels.Item;
import Server.SharedClientModels.MarketOffer;
import Server.SharedClientModels.User;
import java.util.ArrayList;

public interface IGrandExchangeDatabaseServer
{
    User login(User user);
    void register(User user);
    ArrayList<MarketOffer> getSellingOffers();
    boolean sellItem(MarketOffer offer);
    ArrayList<Item> getBackPackItems(int userId);
    void addItemToBackPack(Item item, int userId);
    boolean deleteItemFromBackPack(Item item, int userId);
}