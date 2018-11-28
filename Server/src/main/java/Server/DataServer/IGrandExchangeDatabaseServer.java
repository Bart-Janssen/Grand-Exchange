package Server.DataServer;

import Server.SharedClientModels.MarketOffer;
import Server.SharedClientModels.User;
import java.util.ArrayList;

public interface IGrandExchangeDatabaseServer
{
    boolean login(User user);
    void register(User user);
    ArrayList<MarketOffer> getSellingOffers();
    boolean sellItem(MarketOffer offer);
}