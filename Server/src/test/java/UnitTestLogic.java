import Server.Factory.ServerFactory;
import Server.Models.DatabaseServerType;
import Server.ServerLogic.IGrandExchangeServerLogic;
import Server.SharedClientModels.*;
import org.junit.Before;
import org.junit.Test;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static org.junit.Assert.*;

public class UnitTestLogic
{
    private IGrandExchangeServerLogic logic;

    @Before
    public void setup()
    {
        logic = ServerFactory.getInstance().makeNewGrandExchangeServerLogic(DatabaseServerType.HASHMAP);
    }

    @Test
    public void testCalculatePriceCanWear()//Working
    {
        int days = -0;
        String date = new SimpleDateFormat("dd MM yyyy").format(Calendar.getInstance().getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
        Calendar calendar = Calendar.getInstance();
        date = getDate(days, date, dateFormat, calendar);
        Item weapon = new Weapon(1, 200, AttackStyle.RANGED, "Staff");
        weapon.subtractItemHealth(0);
        weapon.setObtainDate(date);
        assertEquals(900300, logic.calculateItemPrice(new User("a", "p", 100, 1,0), weapon));
    }

    @Test
    public void testCalculatePriceCantWear()//Working
    {
        int days = -0;
        String date = new SimpleDateFormat("dd MM yyyy").format(Calendar.getInstance().getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
        Calendar calendar = Calendar.getInstance();
        date = getDate(days, date, dateFormat, calendar);
        Item weapon = new Weapon(1, 200, AttackStyle.RANGED, "Staff");
        weapon.subtractItemHealth(0);
        weapon.setObtainDate(date);
        assertEquals(810299, logic.calculateItemPrice(new User("a", "p", 99, 1,0), weapon));
    }

    @Test
    public void testCalculatePriceDescendingOnWeaponLevelWithoutMarket()//Working
    {
        logic.getSellOffers().clear();
        Item weapon = new Weapon(1, 200, AttackStyle.RANGED, "Staff");
        assertEquals(900300, logic.calculateItemPrice(new User("a", "p", 100, 1,0), weapon));

        weapon = new Weapon(1, 180, AttackStyle.RANGED, "Staff");
        assertEquals(810280, logic.calculateItemPrice(new User("a", "p", 100, 1,0), weapon));

        weapon = new Weapon(1, 160, AttackStyle.RANGED, "Staff");
        assertEquals(720260, logic.calculateItemPrice(new User("a", "p", 100, 1,0), weapon));

        weapon = new Weapon(1, 140, AttackStyle.RANGED, "Staff");
        assertEquals(630240, logic.calculateItemPrice(new User("a", "p", 100, 1,0), weapon));

        weapon = new Weapon(1, 120, AttackStyle.RANGED, "Staff");
        assertEquals(540220, logic.calculateItemPrice(new User("a", "p", 100, 1,0), weapon));

        weapon = new Weapon(1, 100, AttackStyle.RANGED, "Staff");
        assertEquals(450200, logic.calculateItemPrice(new User("a", "p", 100, 1,0), weapon));

        weapon = new Weapon(1, 80, AttackStyle.RANGED, "Staff");
        assertEquals(360180, logic.calculateItemPrice(new User("a", "p", 100, 1,0), weapon));

        weapon = new Weapon(1, 60, AttackStyle.RANGED, "Staff");
        assertEquals(270160, logic.calculateItemPrice(new User("a", "p", 100, 1,0), weapon));

        weapon = new Weapon(1, 40, AttackStyle.RANGED, "Staff");
        assertEquals(180140, logic.calculateItemPrice(new User("a", "p", 100, 1,0), weapon));

        weapon = new Weapon(1, 20, AttackStyle.RANGED, "Staff");
        assertEquals(90120, logic.calculateItemPrice(new User("a", "p", 100, 1,0), weapon));

        weapon = new Weapon(1, 1, AttackStyle.RANGED, "Staff");
        assertEquals(4601, logic.calculateItemPrice(new User("a", "p", 100, 1,0), weapon));
    }

    @Test
    public void testCalculatePriceDescendingOnUserLevelWithoutMarket()//Working
    {
        logic.getSellOffers().clear();
        Item weapon = new Weapon(1, 200, AttackStyle.RANGED, "Staff");
        assertEquals(900300, logic.calculateItemPrice(new User("a", "p", 100, 1,0), weapon));

        weapon = new Weapon(1, 200, AttackStyle.RANGED, "Staff");
        assertEquals(720280, logic.calculateItemPrice(new User("a", "p", 80, 1,0), weapon));

        weapon = new Weapon(1, 200, AttackStyle.RANGED, "Staff");
        assertEquals(720260, logic.calculateItemPrice(new User("a", "p", 60, 1,0), weapon));

        weapon = new Weapon(1, 200, AttackStyle.RANGED, "Staff");
        assertEquals(720240, logic.calculateItemPrice(new User("a", "p", 40, 1,0), weapon));

        weapon = new Weapon(1, 200, AttackStyle.RANGED, "Staff");
        assertEquals(720220, logic.calculateItemPrice(new User("a", "p", 20, 1,0), weapon));

        weapon = new Weapon(1, 200, AttackStyle.RANGED, "Staff");
        assertEquals(720201, logic.calculateItemPrice(new User("a", "p", 1, 1,0), weapon));
    }

    @Test
    public void testCalculatePriceDescendingOnWeaponHealthWithoutMarket()//Working
    {
        logic.getSellOffers().clear();
        Item weapon = new Weapon(1, 200, AttackStyle.RANGED, "Staff");
        weapon.subtractItemHealth(0);
        assertEquals(900300, logic.calculateItemPrice(new User("a", "p", 100, 1,0), weapon));

        weapon = new Weapon(1, 200, AttackStyle.RANGED, "Staff");
        weapon.subtractItemHealth(20);
        assertEquals(720240, logic.calculateItemPrice(new User("a", "p", 100, 1,0), weapon));

        weapon = new Weapon(1, 200, AttackStyle.RANGED, "Staff");
        weapon.subtractItemHealth(40);
        assertEquals(540180, logic.calculateItemPrice(new User("a", "p", 100, 1,0), weapon));

        weapon = new Weapon(1, 200, AttackStyle.RANGED, "Staff");
        weapon.subtractItemHealth(60);
        assertEquals(360120, logic.calculateItemPrice(new User("a", "p", 100, 1,0), weapon));

        weapon = new Weapon(1, 200, AttackStyle.RANGED, "Staff");
        weapon.subtractItemHealth(80);
        assertEquals(180060, logic.calculateItemPrice(new User("a", "p", 100, 1,0), weapon));

        weapon = new Weapon(1, 200, AttackStyle.RANGED, "Staff");
        weapon.subtractItemHealth(100);
        assertEquals(-1, logic.calculateItemPrice(new User("a", "p", 100, 1,0), weapon));
    }

    @Test
    public void testCalculatePriceDescendingOnWeaponDateWithoutMarket()//Working
    {
        logic.getSellOffers().clear();

        String date = new SimpleDateFormat("dd MM yyyy").format(Calendar.getInstance().getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
        Calendar calendar = Calendar.getInstance();

        Item weapon = new Weapon(1, 200, AttackStyle.RANGED, "Staff");
        weapon.setObtainDate(getDate(-0, date, dateFormat, calendar));
        assertEquals(900300, logic.calculateItemPrice(new User("a", "p", 100, 1,0), weapon));

        weapon = new Weapon(1, 200, AttackStyle.RANGED, "Staff");
        weapon.setObtainDate(getDate(-10, date, dateFormat, calendar));
        assertEquals(900300, logic.calculateItemPrice(new User("a", "p", 100, 1,0), weapon));

        weapon = new Weapon(1, 200, AttackStyle.RANGED, "Staff");
        weapon.setObtainDate(getDate(-11, date, dateFormat, calendar));
        assertEquals(891300, logic.calculateItemPrice(new User("a", "p", 100, 1,0), weapon));

        weapon = new Weapon(1, 200, AttackStyle.RANGED, "Staff");
        weapon.setObtainDate(getDate(-149, date, dateFormat, calendar));
        assertEquals(774300, logic.calculateItemPrice(new User("a", "p", 100, 1,0), weapon));

        weapon = new Weapon(1, 200, AttackStyle.RANGED, "Staff");
        weapon.setObtainDate(getDate(-150, date, dateFormat, calendar));
        assertEquals(732300, logic.calculateItemPrice(new User("a", "p", 100, 1,0), weapon));
    }

    @Test
    public void testCalculatePriceWeaponNeedsToBeRepaired()//Working
    {
        int days = -0;
        String date = new SimpleDateFormat("dd MM yyyy").format(Calendar.getInstance().getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
        Calendar calendar = Calendar.getInstance();
        date = getDate(days, date, dateFormat, calendar);
        Item weapon = new Weapon(1, 200, AttackStyle.RANGED, "Staff");
        weapon.subtractItemHealth(100);
        weapon.setObtainDate(date);
        assertEquals(-1, logic.calculateItemPrice(new User("a", "p", 100, 1,0), weapon));
    }

    @Test
    public void testSellItem()//Working
    {
        int days = -0;
        String date = new SimpleDateFormat("dd MM yyyy").format(Calendar.getInstance().getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
        Calendar calendar = Calendar.getInstance();
        date = getDate(days, date, dateFormat, calendar);
        Item weapon = new Weapon(1, 200, AttackStyle.RANGED, "Staff");
        weapon.subtractItemHealth(0);
        weapon.setObtainDate(date);
        assertTrue(logic.sellItem(new MarketOffer(1,1,1, weapon, MarketOfferType.SELL)));
        ArrayList<MarketOffer> list = logic.getSellOffers();
        assertEquals(1, list.get(list.size()-1).getPrice());

    }

    private String getDate(int days, String date, SimpleDateFormat dateFormat, Calendar calendar)//Working
    {
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
        return date;
    }

    @Test
    public void testLogin()
    {
        User user = new User("username", "password", 100, -1, -1);
        User authenticatedUser = logic.login(user);
        assertNotNull(authenticatedUser);
    }

    @Test
    public void testLoginFailed()
    {
        User user = new User("", "", 100, -1, -1);
        User authenticatedUser = logic.login(user);
        assertNull(authenticatedUser);
    }
}