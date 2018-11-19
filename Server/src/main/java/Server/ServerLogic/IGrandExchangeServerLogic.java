package Server.ServerLogic;

import Server.Models.WebSocketMessage;
import Server.SharedClientModels.Item;
import Server.SharedClientModels.MarketOffer;
import Server.SharedClientModels.User;

import java.util.ArrayList;

public interface IGrandExchangeServerLogic
{
    boolean login(User user);
    boolean sellItem(int price, User user, Item item);
    int calculateItemPrice(User user, Item item);
    ArrayList<MarketOffer> getSellOffers();
}