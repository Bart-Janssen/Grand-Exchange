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
        users.add(new User("", ""));

        sellOffers.add(new MarketOffer(496360, new Item(1, 80, AttackStyle.MELEE, "Sword"), MarketOfferType.SELL, new User("", "", 100, 1)));
        sellOffers.add(new MarketOffer(507599, new Item(1, 165, AttackStyle.MAGIC, "Staff"), MarketOfferType.SELL, new User("", "", 59, 2)));
        sellOffers.add(new MarketOffer(850850, new Item(1, 75, AttackStyle.RANGED, "Bow"), MarketOfferType.SELL, new User("", "", 100, 3)));
        sellOffers.add(new MarketOffer(39853, new Item(1, 65, AttackStyle.MELEE, "Sword"), MarketOfferType.SELL, new User("", "", 63, 1)));
        sellOffers.add(new MarketOffer(163575, new Item(1, 40, AttackStyle.MELEE, "Sword"), MarketOfferType.SELL, new User("", "", 57, 5)));
        sellOffers.add(new MarketOffer(488, new Item(1, 3, AttackStyle.MAGIC, "Staff"), MarketOfferType.SELL, new User("", "", 61, 8)));
        sellOffers.add(new MarketOffer(618011, new Item(1, 53, AttackStyle.RANGED, "Bow"), MarketOfferType.SELL, new User("", "", 95, 6)));
        sellOffers.add(new MarketOffer(900300, new Item(1, 200, AttackStyle.RANGED, "Bow"), MarketOfferType.SELL, new User("", "", 100, 5)));
        sellOffers.add(new MarketOffer(90904, new Item(1, 68, AttackStyle.MAGIC, "Staff"), MarketOfferType.SELL, new User("", "", 13, 6)));
        sellOffers.add(new MarketOffer(301321, new Item(1, 33, AttackStyle.MAGIC, "Staff"), MarketOfferType.SELL, new User("", "", 65, 9)));

        buyOffers.add(new MarketOffer(300000, new Item(1, 100, AttackStyle.MELEE, "Sword"), MarketOfferType.BUY, new User("", "", 65, 3)));
        buyOffers.add(new MarketOffer(500000, new Item(1, 165, AttackStyle.MAGIC, "Staff"), MarketOfferType.BUY, new User("", "", 99, 7)));
        buyOffers.add(new MarketOffer(1000000, new Item(1, 198, AttackStyle.RANGED, "Bow"), MarketOfferType.BUY, new User("", "", 35, 2)));
        buyOffers.add(new MarketOffer(50000, new Item(1, 50, AttackStyle.MELEE, "Sword"), MarketOfferType.BUY, new User("", "", 70, 1)));
        buyOffers.add(new MarketOffer(150000, new Item(1, 41, AttackStyle.MELEE, "Sword"), MarketOfferType.BUY, new User("", "", 63, 10)));
        buyOffers.add(new MarketOffer(8000, new Item(1, 7, AttackStyle.MAGIC, "Staff"), MarketOfferType.BUY, new User("", "", 23, 2)));
        buyOffers.add(new MarketOffer(750000, new Item(1, 88, AttackStyle.RANGED, "Bow"), MarketOfferType.BUY, new User("", "", 68, 9)));
        buyOffers.add(new MarketOffer(980000, new Item(1, 200, AttackStyle.RANGED, "Bow"), MarketOfferType.BUY, new User("", "", 100, 3)));
        buyOffers.add(new MarketOffer(106000, new Item(1, 70, AttackStyle.MAGIC, "Staff"), MarketOfferType.BUY, new User("", "", 50, 1)));
        buyOffers.add(new MarketOffer(128000, new Item(1, 26, AttackStyle.MAGIC, "Staff"), MarketOfferType.BUY, new User("", "", 33, 5)));
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
    public void register(User user)
    {
        users.add(user);
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
}