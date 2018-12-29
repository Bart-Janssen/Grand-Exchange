package DataServer.DataServerLogic;

import DataServer.Database.IDatabaseConnection;
import DataServer.SharedServerModels.Item;
import DataServer.SharedServerModels.MarketOffer;
import DataServer.SharedServerModels.User;

import java.util.ArrayList;

public class DataServerLogic implements IDataServerLogic
{
    private IDatabaseConnection database;

    public DataServerLogic(IDatabaseConnection database)
    {
        this.database = database;
    }

    public User login(User user)
    {
        return database.login(user);
    }

    public void register(User user)
    {

    }

    @Override
    public ArrayList<Item> getBackPackItems(int userId)
    {
        ArrayList<Item> items = database.getBackPackItems(userId);
        //TODO:
        return items;
    }

    @Override
    public boolean addItemToBackPack(Item item, int userId)
    {
        return database.addItemToBackPack(item, userId);
    }

    @Override
    public boolean deleteItemFromBackPack(int itemId, int userId)
    {
        return database.deleteItemFromBackPack(itemId, userId);
    }

    @Override
    public ArrayList<MarketOffer> getMarketOffers(int userId)
    {
        return database.getMarketOffers(userId);
    }

    @Override
    public boolean sellItem(MarketOffer offer)
    {
        return database.sellItem(offer);
    }

    @Override
    public boolean cancelOffer(int offerId)
    {
        return database.cancelOffer(offerId);
    }

    @Override
    public ArrayList<MarketOffer> getSearchOffers(String searchQuery, int userId)
    {
        return database.getSearchOffers(searchQuery, userId);
    }
}