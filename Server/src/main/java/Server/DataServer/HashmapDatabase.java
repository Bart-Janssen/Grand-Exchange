package Server.DataServer;

import Server.SharedClientModels.AttackStyle;
import Server.SharedClientModels.Item;
import Server.SharedClientModels.MarketOffer;
import Server.SharedClientModels.User;
import java.util.ArrayList;

public class HashmapDatabase implements IGrandExchangeDatabaseServer
{
    ArrayList<MarketOffer> sellOffers = new ArrayList<>();
    ArrayList<MarketOffer> buyOffers = new ArrayList<>();
    ArrayList<User> users = new ArrayList<>();

    public HashmapDatabase()
    {
        sellOffers.add(new MarketOffer(496360, new Item(80, AttackStyle.MELEE, "Sword"), new User("", "", 100)));
        sellOffers.add(new MarketOffer(507599, new Item(165, AttackStyle.MAGIC, "Staff"), new User("", "", 59)));
        sellOffers.add(new MarketOffer(850850, new Item(75, AttackStyle.RANGED, "Bow"), new User("", "", 100)));
        sellOffers.add(new MarketOffer(39853, new Item(65, AttackStyle.MELEE, "Sword"), new User("", "", 63)));
        sellOffers.add(new MarketOffer(163575, new Item(40, AttackStyle.MELEE, "Sword"), new User("", "", 57)));
        sellOffers.add(new MarketOffer(488, new Item(3, AttackStyle.MAGIC, "Staff"), new User("", "", 61)));
        sellOffers.add(new MarketOffer(618011, new Item(53, AttackStyle.RANGED, "Bow"), new User("", "", 95)));
        sellOffers.add(new MarketOffer(900300, new Item(200, AttackStyle.RANGED, "Bow"), new User("", "", 100)));
        sellOffers.add(new MarketOffer(90904, new Item(68, AttackStyle.MAGIC, "Staff"), new User("", "", 13)));
        sellOffers.add(new MarketOffer(301321, new Item(33, AttackStyle.MAGIC, "Staff"), new User("", "", 65)));

        buyOffers.add(new MarketOffer(300000, new Item(100, AttackStyle.MELEE, "Sword"), new User("", "", 65)));
        buyOffers.add(new MarketOffer(500000, new Item(165, AttackStyle.MAGIC, "Staff"), new User("", "", 99)));
        buyOffers.add(new MarketOffer(1000000, new Item(198, AttackStyle.RANGED, "Bow"), new User("", "", 35)));
        buyOffers.add(new MarketOffer(50000, new Item(50, AttackStyle.MELEE, "Sword"), new User("", "", 70)));
        buyOffers.add(new MarketOffer(150000, new Item(41, AttackStyle.MELEE, "Sword"), new User("", "", 63)));
        buyOffers.add(new MarketOffer(8000, new Item(7, AttackStyle.MAGIC, "Staff"), new User("", "", 23)));
        buyOffers.add(new MarketOffer(750000, new Item(88, AttackStyle.RANGED, "Bow"), new User("", "", 68)));
        buyOffers.add(new MarketOffer(980000, new Item(200, AttackStyle.RANGED, "Bow"), new User("", "", 100)));
        buyOffers.add(new MarketOffer(106000, new Item(70, AttackStyle.MAGIC, "Staff"), new User("", "", 50)));
        buyOffers.add(new MarketOffer(128000, new Item(26, AttackStyle.MAGIC, "Staff"), new User("", "", 33)));
    }

    @Override
    public boolean login(User user)
    {
        for (User u : users)
        {
            if (u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword()))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public void register(User user)
    {
        users.add(user);
    }

    @Override
    public ArrayList<MarketOffer> getSellingItems(String itemName)
    {
        ArrayList<MarketOffer> tempList = new ArrayList<>();
        for (MarketOffer offer : sellOffers)
        {
            if (offer.getItem().getAttackStyle() == AttackStyle.MAGIC)
            {
                tempList.add(offer);
            }
        }
        return tempList;
    }

    @Override
    public boolean sellItem(MarketOffer offer)
    {
        sellOffers.add(offer);
        return true;
    }
}