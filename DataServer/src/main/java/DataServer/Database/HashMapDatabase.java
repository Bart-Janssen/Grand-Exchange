package DataServer.Database;

import DataServer.SharedServerModels.Item;
import DataServer.SharedServerModels.MarketOffer;
import DataServer.SharedServerModels.User;

import java.util.ArrayList;
import java.util.HashMap;

public class HashMapDatabase implements IDatabaseConnection
{
    private HashMap<String, String> users = new HashMap<>();

    public HashMapDatabase()
    {
        users.put("bart", "bart");
        users.put("", "");
    }

    @Override
    public User login(User user)
    {
        try
        {
            return user;//users.get(user.getUsername()).equals(user.getPassword());
        }
        catch (Exception ex)
        {
            System.out.println("User: " + user.getUsername() + " doesn't exist, cant login.");
        }
        return null;
    }

    @Override
    public void register(User user)
    {

    }

    @Override
    public ArrayList<Item> getBackPackItems(int id)
    {
        return null;
    }

    @Override
    public boolean addItemToBackPack(Item item, int userId)
    {
        return false;
    }

    @Override
    public boolean deleteItemFromBackPack(int itemId, int userId)
    {
        return false;
    }

    @Override
    public ArrayList<MarketOffer> getMarketOffers(int userId)
    {
        return null;
    }
}