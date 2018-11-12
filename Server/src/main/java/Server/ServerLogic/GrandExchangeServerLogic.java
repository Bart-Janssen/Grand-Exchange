package Server.ServerLogic;

import Server.DataServer.IGrandExchangeDatabaseServer;
import Server.Models.WebSocketMessage;
import Server.SharedClientModels.Item;
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
    public WebSocketMessage sellItem(User user, Item item)
    {
        WebSocketMessage webSocketMessage = new WebSocketMessage();
        webSocketMessage.setOperation(MessageType.SELLITEM);
        webSocketMessage.setMessage("Item sold.");
        webSocketMessage.setUser(user);
        int price = calculatePrice(user, item);
        //TODO: sell item to Market
        if (price == -1) webSocketMessage.setMessage("Cannot sell item, repair it first.");
        webSocketMessage.setItem(item);
        return webSocketMessage;
    }

    /*public int calculateDateWeaponState(Item item)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
        long difference;
        try
        {
            int power = 0;
            if (item instanceof Armor)
            {
                power = ((Armor)item).getDefence();
            }
            if (item instanceof Weapon)
            {
                power = ((Weapon)item).getDamage();
            }
            difference = TimeUnit.DAYS.convert(dateFormat.parse(new SimpleDateFormat("dd MM yyyy").format(Calendar.getInstance().getTime())).getTime() - dateFormat.parse(item.getObtainDate()).getTime(), TimeUnit.MILLISECONDS);
            System.out.println(difference);
            if (difference <= 10) return 15 * item.getItemLevel();
            if (difference >= 150) return 0;
            return ((item.getItemLevel() * item.getItemHealth()) * 1000) + ((power * item.getItemLevel()) + ((item.getAttackStyle().getValue() * (15 * item.getItemLevel()) - (int)Math.floor((double)difference / 10) * item.getItemLevel())));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return 0;
    }*/

    private void calculateDatePrice(Item item)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
        long difference;
        int defaultPrice = 15 * item.getAttackStyle().getValue() * item.getItemLevel();
        try
        {
            difference = TimeUnit.DAYS.convert(dateFormat.parse(new SimpleDateFormat("dd MM yyyy").format(Calendar.getInstance().getTime())).getTime() - dateFormat.parse(item.getObtainDate()).getTime(), TimeUnit.MILLISECONDS);
            if (difference <= 10) item.setPrice(item.getPrice() + defaultPrice);
            if (difference >= 150) item.setPrice(item.getPrice() + item.getAttackStyle().getValue() * item.getItemLevel());
            item.setPrice(item.getPrice() + defaultPrice - (int)((int)Math.floor((double)difference / 10) * (defaultPrice * 0.05))); //5 percent
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

        calculateMarketPrice(item);

        calculateWeaponHealth(item);
        System.out.println("Wep health: " + item.getPrice());


        int price = item.getPrice();
        System.out.println("price: " + price);

        return price;
    }

    private void calculateMarketPrice(Item item)
    {
        databaseServer.
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