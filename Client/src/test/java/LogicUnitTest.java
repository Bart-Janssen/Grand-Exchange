import org.junit.Before;
import org.junit.Test;
import sample.Logic.GrandExchangeLogic;
import sample.Models.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import static org.junit.Assert.assertEquals;

public class LogicUnitTest
{
    GrandExchangeLogic logic;

    @Before
    public void setup()
    {
        logic = new GrandExchangeLogic(null, null);//ClientFactory.getInstance().makeNewIGrandExchangeLogic(null, WebSocketType.WEBSOCKETSERVER);
    }

    @Test
    public void testDateWeaponState90Days()
    {
        int amount = 90;
        String date = new SimpleDateFormat("dd MM yyyy").format(Calendar.getInstance().getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
        Calendar calendar = Calendar.getInstance();
        try
        {
            calendar.setTime(dateFormat.parse(date));
            calendar.add(Calendar.DATE, amount);
            date = dateFormat.format(calendar.getTime());
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        Item armor = new Armor(100, 15 , AttackStyle.MAGIC);
        armor.setObtainDate(date);
       /* try
        {
            armor.setObtainDate(new SimpleDateFormat("dd MM yyyy").format(new SimpleDateFormat("dd MM yyyy").parse("04 04 2019")));

        } catch (Exception e) {
            e.printStackTrace();
        }*/
        assertEquals(600, logic.calculateDateWeaponState(armor));
    }

    @Test
    public void testDateWeaponState0Days()
    {
        int amount = 0;
        String date = new SimpleDateFormat("dd MM yyyy").format(Calendar.getInstance().getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
        Calendar calendar = Calendar.getInstance();
        try
        {
            calendar.setTime(dateFormat.parse(date));
            calendar.add(Calendar.DATE, amount);
            date = dateFormat.format(calendar.getTime());
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        Item armor = new Weapon(100, 15 , AttackStyle.MAGIC);
        armor.setObtainDate(date);
        assertEquals(1500, logic.calculateDateWeaponState(armor));
    }

    @Test
    public void testSellArmor5Days()
    {
        int amount = 5;
        String date = new SimpleDateFormat("dd MM yyyy").format(Calendar.getInstance().getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
        Calendar calendar = Calendar.getInstance();
        try
        {
            calendar.setTime(dateFormat.parse(date));
            calendar.add(Calendar.DATE, amount);
            date = dateFormat.format(calendar.getTime());
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        Item armor = new Armor(150, 100 , AttackStyle.MELEE);
        armor.setDamagedState(8);
        armor.setObtainDate(date);
        assertEquals(1305000, logic.sellItem(armor));
    }
}