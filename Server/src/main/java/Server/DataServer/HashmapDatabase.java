package Server.DataServer;

import Server.SharedClientModels.AttackStyle;
import Server.SharedClientModels.Item;
import Server.SharedClientModels.MarketOffer;
import Server.SharedClientModels.User;

import java.util.ArrayList;

public class HashmapDatabase implements IGrandExchangeDatabaseServer
{
    ArrayList<MarketOffer> offers = new ArrayList<>();

    public HashmapDatabase()
    {
        offers.add(new MarketOffer(496360, new Item(80, AttackStyle.MELEE, "Sword"), new User("", "", 100)));
        offers.add(new MarketOffer(507599, new Item(165, AttackStyle.MAGIC, "Staff"), new User("", "", 59)));
        offers.add(new MarketOffer(850850, new Item(75, AttackStyle.RANGED, "Bow"), new User("", "", 100)));
        offers.add(new MarketOffer(39853, new Item(65, AttackStyle.MELEE, "Sword"), new User("", "", 63)));
        offers.add(new MarketOffer(163575, new Item(40, AttackStyle.MELEE, "Sword"), new User("", "", 57)));
        offers.add(new MarketOffer(488, new Item(3, AttackStyle.MAGIC, "Staff"), new User("", "", 61)));
        offers.add(new MarketOffer(618011, new Item(53, AttackStyle.RANGED, "Bow"), new User("", "", 95)));
        offers.add(new MarketOffer(2520600, new Item(200, AttackStyle.RANGED, "Bow"), new User("", "", 100)));
        offers.add(new MarketOffer(90904, new Item(68, AttackStyle.MAGIC, "Staff"), new User("", "", 13)));
        offers.add(new MarketOffer(301321, new Item(33, AttackStyle.MAGIC, "Staff"), new User("", "", 65)));

        
    }

    @Override
    public boolean login(User user)
    {
        return true;
    }

    @Override
    public void register(User user)
    {

    }

    @Override
    public ArrayList<MarketOffer> getSellingItems(String itemName)
    {
        ArrayList<MarketOffer> tempList = new ArrayList<>();
        for (MarketOffer offer : offers)
        {
            if (offer.getItem().getAttackStyle() == AttackStyle.MAGIC)
            {
                tempList.add(offer);
            }
        }
        return tempList;
    }
}