package Server.DataServer;

import Server.SharedClientModels.Item;
import Server.SharedClientModels.MarketOffer;
import Server.SharedClientModels.User;

import java.util.ArrayList;

public interface IGrandExchangeDatabaseServer
{
    boolean login(User user);
    void register(User user);
    ArrayList<MarketOffer> getSellingItems();
    boolean sellItem(MarketOffer offer);
}