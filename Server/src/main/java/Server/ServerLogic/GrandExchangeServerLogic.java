package Server.ServerLogic;

import Server.DataServer.IGrandExchangeDatabaseServer;
import Server.Models.WebSocketMessage;
import Server.SharedClientModels.Item;
import Server.SharedClientModels.MarketOffer;
import Server.SharedClientModels.MessageType;
import Server.SharedClientModels.User;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class GrandExchangeServerLogic implements IGrandExchangeServerLogic
{
    private IGrandExchangeDatabaseServer databaseServer;

    public GrandExchangeServerLogic(IGrandExchangeDatabaseServer databaseServer)
    {
        this.databaseServer = databaseServer;
    }

    @Override
    public boolean login(User user)
    {
        return databaseServer.login(user);
    }

    @Override
    public boolean sellItem(int price, User user, Item item)
    {
        return databaseServer.sellItem(new MarketOffer(price, item, user));
    }

    @Override
    public int calculateItemPrice(User user, Item item)
    {
        return calculatePrice(user, item);
    }

    private void calculateDatePrice(Item item)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
        long difference;
        int defaultPrice = 15 * item.getAttackStyle().getValue() * item.getItemLevel();
        try
        {
            difference = TimeUnit.DAYS.convert(dateFormat.parse(new SimpleDateFormat("dd MM yyyy").format(Calendar.getInstance().getTime())).getTime() - dateFormat.parse(item.getObtainDate()).getTime(), TimeUnit.MILLISECONDS);
            if (difference <= 10)
            {
                item.setPrice(item.getPrice() + defaultPrice);
                return;
            }
            if (difference >= 150)
            {
                item.setPrice(item.getPrice() + item.getAttackStyle().getValue() * item.getItemLevel());
                return;
            }
            item.setPrice(item.getPrice() + defaultPrice - (int)((int)Math.floor((double)difference / 10) * (defaultPrice * 0.05))); //5 percent
            return;
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        item.setPrice(item.getPrice() + defaultPrice);
    }

    public int calculatePrice(User user, Item item)
    {
        if (item.getItemHealth() == 0) return -1;

        calculateWeaponPrice(user, item);
        System.out.println("Wep price: " + item.getPrice());

        calculateDatePrice(item);
        System.out.println("date price: " + item.getPrice());

        calculateWeaponHealth(item);
        System.out.println("Wep health: " + item.getPrice());

        calculateMarketPrice(item);
        System.out.println("Market price: " + item.getPrice());

        return item.getPrice();
    }

    private void calculateMarketPrice(Item item)
    {
        int totalPrices = item.getPrice();
        int pricesCount = 1;
        for (MarketOffer offer : databaseServer.getSellingItems(item.getName()))
        {
            if (offer.getItem().getItemLevel() > (item.getItemLevel() - item.getItemLevel() * 0.1) && offer.getItem().getItemLevel() < (item.getItemLevel() + item.getItemLevel() * 0.1))
            {
                totalPrices += offer.getPrice();
                pricesCount++;
            }
        }
        if (totalPrices != item.getPrice()) item.setPrice(totalPrices / pricesCount);
        System.out.println(pricesCount);
    }

    private void calculateWeaponHealth(Item item)
    {
        if (item.getItemHealth() == 100) return;
        item.setPrice((item.getPrice() / 100) * item.getItemHealth());
    }

    private void calculateWeaponPrice(User user, Item item)
    {
        int defaultPrice = user.getLevel() + item.getItemLevel();
        if (canPlayerWieldItem(user, item))
        {
            System.out.println("Can wear");
            defaultPrice += item.getItemLevel() * item.getAttackStyle().getValue() * item.getAttackStyle().getValue();
            item.setPrice(item.getPrice() + defaultPrice);
            return;
        }
        System.out.println("can't wear");
        defaultPrice += (int)(item.getItemLevel() * item.getAttackStyle().getValue() * item.getAttackStyle().getValue() * 0.75);
        item.setPrice(item.getPrice() + defaultPrice);
    }

    private boolean canPlayerWieldItem(User user, Item item)
    {
        return user.getLevel() >= ((item.getItemLevel()) / 2);
    }
}