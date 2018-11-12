import Server.DataServer.HashmapDatabase;
import Server.ServerLogic.GrandExchangeServerLogic;
import Server.SharedClientModels.*;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

public class UnitTestLogic
{
    GrandExchangeServerLogic logic;

    @Before
    public void setup()
    {
        logic = new GrandExchangeServerLogic(new HashmapDatabase());//ClientFactory.getInstance().makeNewIGrandExchangeLogic(null, WebSocketType.WEBSOCKETSERVER);
    }

    /*@Test
    public void testCalculateDatePrice()
    {
        int days = -15;
        String date = new SimpleDateFormat("dd MM yyyy").format(Calendar.getInstance().getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
        Calendar calendar = Calendar.getInstance();
        try
        {
            calendar.setTime(dateFormat.parse(date));
            calendar.add(Calendar.DATE, days);
            date = dateFormat.format(calendar.getTime());
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        Item weapon = new Weapon(200, 15 , AttackStyle.MAGIC);
        weapon.setObtainDate(date);
        logic.calculateDatePrice(new User("a", "p", 100), weapon);
        assertEquals(1500, weapon.getPrice());
    }*/

    @Test
    public void testCalculatePrice()
    {
        int days = -0;
        String date = new SimpleDateFormat("dd MM yyyy").format(Calendar.getInstance().getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
        Calendar calendar = Calendar.getInstance();
        try
        {
            calendar.setTime(dateFormat.parse(date));
            calendar.add(Calendar.DATE, days);
            date = dateFormat.format(calendar.getTime());
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        Item weapon = new Weapon(200, 15 , AttackStyle.RANGED, "Staff");
        weapon.subtractItemHealth(0);
        weapon.setObtainDate(date);
        assertEquals(1500, logic.calculatePrice(new User("a", "p", 100), weapon));
    }

    @Test
    public void testSellItem()
    {
        int days = -0;
        String date = new SimpleDateFormat("dd MM yyyy").format(Calendar.getInstance().getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
        Calendar calendar = Calendar.getInstance();
        try
        {
            calendar.setTime(dateFormat.parse(date));
            calendar.add(Calendar.DATE, days);
            date = dateFormat.format(calendar.getTime());
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        Item weapon = new Weapon(200, 15 , AttackStyle.RANGED, "Staff");
        weapon.subtractItemHealth(0);
        weapon.setObtainDate(date);
        assertEquals("Item sold.", logic.sellItem(new User("a", "p", 100), weapon).getMessage());
    }

    @Test
    public void testSellItemNeedRepair()
    {
        int days = -0;
        String date = new SimpleDateFormat("dd MM yyyy").format(Calendar.getInstance().getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
        Calendar calendar = Calendar.getInstance();
        try
        {
            calendar.setTime(dateFormat.parse(date));
            calendar.add(Calendar.DATE, days);
            date = dateFormat.format(calendar.getTime());
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        Item weapon = new Weapon(200, 15 , AttackStyle.RANGED, "Staff");
        weapon.subtractItemHealth(100);
        weapon.setObtainDate(date);
        assertEquals("Cannot sell item, repair it first.", logic.sellItem(new User("a", "p", 100), weapon).getMessage());
    }
}