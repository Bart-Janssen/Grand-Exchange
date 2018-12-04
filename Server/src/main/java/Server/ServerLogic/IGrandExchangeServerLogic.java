package Server.ServerLogic;

import Server.SharedClientModels.Item;
import Server.SharedClientModels.MarketOffer;
import Server.SharedClientModels.User;
import java.util.ArrayList;

public interface IGrandExchangeServerLogic
{
    User login(User user);
    boolean sellItem(MarketOffer offer);
    int calculateItemPrice(User user, Item item);
    ArrayList<MarketOffer> getSellOffers();
    ArrayList<Item> getBackPackItems(int id);
}