package DataServer.Database;

import DataServer.SharedServerModels.*;

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

    private ArrayList<MarketOffer> getList()
    {
        ArrayList<MarketOffer> sellOffers = new ArrayList<>();
        sellOffers.add(new MarketOffer(1, 1, 496360, new Item(1, 80, AttackStyle.MELEE, "Sword"), MarketOfferType.SELL));
        sellOffers.add(new MarketOffer(1, 1, 507599, new Item(1, 165, AttackStyle.MAGIC, "Staff"), MarketOfferType.SELL));
        sellOffers.add(new MarketOffer(1, 1, 850850, new Item(1, 75, AttackStyle.RANGED, "Bow"), MarketOfferType.SELL));
        sellOffers.add(new MarketOffer(1, 1, 39853, new Item(1, 65, AttackStyle.MELEE, "Sword"), MarketOfferType.SELL));
        sellOffers.add(new MarketOffer(1, 1, 163575, new Item(1, 40, AttackStyle.MELEE, "Sword"), MarketOfferType.SELL));
        sellOffers.add(new MarketOffer(1, 1, 488, new Item(1, 3, AttackStyle.MAGIC, "Staff"), MarketOfferType.SELL));
        sellOffers.add(new MarketOffer(1, 1, 618011, new Item(1, 53, AttackStyle.RANGED, "Bow"), MarketOfferType.SELL));
        sellOffers.add(new MarketOffer(1, 1, 900300, new Item(1, 200, AttackStyle.RANGED, "Bow"), MarketOfferType.SELL));
        sellOffers.add(new MarketOffer(1, 1, 90904, new Item(1, 68, AttackStyle.MAGIC, "Staff"), MarketOfferType.SELL));
        sellOffers.add(new MarketOffer(1, 1, 301321, new Item(1, 33, AttackStyle.MAGIC, "Staff"), MarketOfferType.SELL));
        return sellOffers;
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
    public String register(User user)
    {
        return "";
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

    @Override
    public boolean sellItem(MarketOffer offer)
    {
        return false;
    }

    @Override
    public boolean cancelOffer(int offerId)
    {
        return false;
    }

    @Override
    public ArrayList<MarketOffer> getSearchOffers(String searchQuery, int userId)
    {
        return null;
    }

    @Override
    public boolean buyItem(MarketOffer offer, int buyerId)
    {
        return false;
    }

    @Override
    public int getUserCoins(int id)
    {
        return 0;
    }

    @Override
    public ArrayList<MarketOffer> getSellingOffers()
    {
        return getList();
    }
}