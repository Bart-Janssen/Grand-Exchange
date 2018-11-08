package Server.ServerLogic;

import Server.DataServer.IGrandExchangeDatabaseServer;
import Server.SharedClientModels.Armor;
import Server.SharedClientModels.Item;
import Server.SharedClientModels.User;
import Server.SharedClientModels.Weapon;

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
    public boolean sellItem(Item item)
    {
        if (item.getDamagedState() == 100)
        {
            return false;
        }
        int price = calculateDateWeaponState(item);
        if (price != 0)
        {
            item.setPrice(price);
        }
        return true;
    }

    public int calculateDateWeaponState(Item item)
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
            return ((item.getItemLevel() * item.getDamagedState()) * 1000) + ((power * item.getItemLevel()) + ((item.getAttackStyle().getValue() * (15 * item.getItemLevel()) - (int)Math.floor((double)difference / 10) * item.getItemLevel())));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return 0;
    }
}