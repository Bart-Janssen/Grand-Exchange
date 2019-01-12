package Server.DataServer;

import Server.SharedClientModels.*;

import java.util.ArrayList;

public class HashMapDatabase implements IGrandExchangeDatabaseServer
{
    ArrayList<MarketOffer> sellOffers = new ArrayList<>();
    ArrayList<MarketOffer> buyOffers = new ArrayList<>();
    ArrayList<User> users = new ArrayList<>();

    public HashMapDatabase()
    {
        users.add(new User("username", "password"));

        sellOffers.add(new MarketOffer(1, 0, 496360, new Item(1, 80, AttackStyle.MELEE, "Sword"), MarketOfferType.SELL));
        sellOffers.add(new MarketOffer(1, 0, 507599, new Item(1, 165, AttackStyle.MAGIC, "Staff"), MarketOfferType.SELL));
        sellOffers.add(new MarketOffer(1, 0, 850850, new Item(1, 75, AttackStyle.RANGED, "Bow"), MarketOfferType.SELL));
        sellOffers.add(new MarketOffer(1, 0, 39853, new Item(1, 65, AttackStyle.MELEE, "Sword"), MarketOfferType.SELL));
        sellOffers.add(new MarketOffer(1, 0,163575, new Item(1, 40, AttackStyle.MELEE, "Sword"), MarketOfferType.SELL));
        sellOffers.add(new MarketOffer(1, 0,488, new Item(1, 3, AttackStyle.MAGIC, "Staff"), MarketOfferType.SELL));
        sellOffers.add(new MarketOffer(1, 0, 618011, new Item(1, 53, AttackStyle.RANGED, "Bow"), MarketOfferType.SELL));
        sellOffers.add(new MarketOffer(1, 0, 900300, new Item(1, 200, AttackStyle.RANGED, "Bow"), MarketOfferType.SELL));
        sellOffers.add(new MarketOffer(1, 0, 90904, new Item(1, 68, AttackStyle.MAGIC, "Staff"), MarketOfferType.SELL));
        sellOffers.add(new MarketOffer(1, 0, 301321, new Item(1, 33, AttackStyle.MAGIC, "Staff"), MarketOfferType.SELL));

        buyOffers.add(new MarketOffer(1, 0, 300000, new Item(1, 100, AttackStyle.MELEE, "Sword"), MarketOfferType.BUY));
        buyOffers.add(new MarketOffer(1, 0, 500000, new Item(1, 165, AttackStyle.MAGIC, "Staff"), MarketOfferType.BUY));
        buyOffers.add(new MarketOffer(1, 0, 1000000, new Item(1, 198, AttackStyle.RANGED, "Bow"), MarketOfferType.BUY));
        buyOffers.add(new MarketOffer(1, 0, 50000, new Item(1, 50, AttackStyle.MELEE, "Sword"), MarketOfferType.BUY));
        buyOffers.add(new MarketOffer(1, 0, 150000, new Item(1, 41, AttackStyle.MELEE, "Sword"), MarketOfferType.BUY));
        buyOffers.add(new MarketOffer(1, 0, 8000, new Item(1, 7, AttackStyle.MAGIC, "Staff"), MarketOfferType.BUY));
        buyOffers.add(new MarketOffer(1, 0, 750000, new Item(1, 88, AttackStyle.RANGED, "Bow"), MarketOfferType.BUY));
        buyOffers.add(new MarketOffer(1, 0, 750000, new Item(1, 88, AttackStyle.RANGED, "Bow"), MarketOfferType.BUY));
        buyOffers.add(new MarketOffer(1, 0, 980000, new Item(1, 200, AttackStyle.RANGED, "Bow"), MarketOfferType.BUY));
        buyOffers.add(new MarketOffer(1, 0, 106000, new Item(1, 70, AttackStyle.MAGIC, "Staff"), MarketOfferType.BUY));
        buyOffers.add(new MarketOffer(1, 0, 128000, new Item(1, 26, AttackStyle.MAGIC, "Staff"), MarketOfferType.BUY));
    }

    @Override
    public User login(User user)
    {
        for (User u : users)
        {
            if (u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword()))
            {
                return u;
            }
        }
        return null;
    }

    @Override
    public String register(User user)
    {
        users.add(user);
        return "success";
    }

    @Override
    public ArrayList<MarketOffer> getSellingOffers()
    {
        return sellOffers;
    }

    @Override
    public boolean sellItem(MarketOffer offer)
    {
        sellOffers.add(offer);
        return true;
    }

    @Override
    public ArrayList<Item> getBackPackItems(int id)
    {
        return null;
    }

    @Override
    public void addItemToBackPack(Item item, int userId)
    {

    }

    @Override
    public boolean deleteItemFromBackPack(Item item, int userId)
    {
        return false;
    }

    @Override
    public ArrayList<MarketOffer> getMarketOffers(int userId)
    {
        return null;
    }

    @Override
    public boolean cancelOffer(MarketOffer offer)
    {
        return false;
    }

    @Override
    public ArrayList<MarketOffer> getSearchOffers(String searchQuery, int userId)
    {
        return buyOffers;
    }

    @Override
    public boolean buyItem(MarketOffer marketOffer, int buyerId)
    {
        return false;
    }

    @Override
    public int getUserCoins(int id)
    {
        return 0;
    }
}