package Server.ServerLogic;

import Server.DataServer.IGrandExchangeDatabaseServer;
import Server.SharedClientModels.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GrandExchangeServerLogic implements IGrandExchangeServerLogic
{
    private IGrandExchangeDatabaseServer databaseServer;
    private static final int WEAPON_BROKEN_STATE = -1;
    private static final int HALF = 2;
    private static final int DATE_MULTIPLIER = 15;

    public GrandExchangeServerLogic(IGrandExchangeDatabaseServer databaseServer)
    {
        this.databaseServer = databaseServer;
    }

    @Override
    public User login(User user)
    {
        return databaseServer.login(user);
    }

    @Override
    public boolean sellItem(MarketOffer offer)
    {
        return databaseServer.sellItem(offer);
    }

    @Override
    public int calculateItemPrice(User user, Item item)
    {
        if (item.getItemHealth() == 0) return WEAPON_BROKEN_STATE;

        calculateWeaponPrice(user, item);
        System.out.println("Wep price: " + item.getPrice());

        calculateDatePrice(item);
        System.out.println("date price: " + item.getPrice());

        calculateWeaponHealth(item);
        System.out.println("Wep health: " + item.getPrice());

        calculateAverageMarketSellingItemPrice(item);
        System.out.println("MarketSelling price: " + item.getPrice());

        calculateAverageMarketBuyingItemPrice(user, item);
        System.out.println("MarketBuying price: " + item.getPrice());

        return item.getPrice();
    }

    private void calculateAverageMarketBuyingItemPrice(User user, Item item)
    {

    }

    @Override
    public ArrayList<MarketOffer> getSellOffers()
    {
        return databaseServer.getSellingOffers();
    }

    @Override
    public ArrayList<Item> getBackPackItems(int userId)
    {
        return databaseServer.getBackPackItems(userId);
    }

    @Override
    public ArrayList<Item> generateItem(int userId)
    {
        boolean weapon = new Random().nextBoolean();
        if (weapon) return generateNewWeapon(userId);
        return generateNewArmor(userId);
    }

    private ArrayList<Item> generateNewArmor(int userId)
    {
        ArrayList<String[]> names = new ArrayList<>();
        String[] ranged = {"RANGED", "RangedBody", "RangedLegs", "RangedCoif", "RangedShield"};
        String[] melee = {"MELEE", "MeleeBody", "MeleeLegs", "MeleeHelmet", "MeleeShield"};
        String[] magic = {"MAGIC", "MagicBody", "MagicLegs", "MagicHat", "MagicShield"};
        names.add(ranged);
        names.add(melee);
        names.add(magic);

        int typeIndex = new Random().nextInt(names.size());

        int nameIndex = new Random().nextInt(names.get(typeIndex).length - 1) + 1;

        AttackStyle attackStyle = AttackStyle.valueOf(names.get(typeIndex)[0]);
        System.out.println("Attack style enum: " + attackStyle);

        int level = new Random().nextInt(200) + 1;
        System.out.println("level: " + level);

        String name = names.get(typeIndex)[nameIndex];
        System.out.println("Name: " + name);

        int health = new Random().nextInt(100);
        System.out.println("Health: " + health);

        int day = new Random().nextInt(150);
        int days = -day;
        String date = new SimpleDateFormat("dd MM yyyy").format(Calendar.getInstance().getTime());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
        Calendar calendar = Calendar.getInstance();
        date = getDate(days, date, dateFormat, calendar);
        System.out.println("Date: " + date);
        databaseServer.addItemToBackPack(new Armor(-1, level, attackStyle, name, health, date), userId);
        return getBackPackItems(userId);
    }

    private ArrayList<Item> generateNewWeapon(int userId)
    {
        int index = new Random().nextInt(AttackStyle.values().length);
        AttackStyle attackStyle = AttackStyle.values()[index];
        System.out.println("Attack style enum: " + attackStyle);

        int level = new Random().nextInt(200) + 1;
        System.out.println("level: " + level);

        String[] names = {"Bow", "Sword", "Staff"};
        String name = names[index];
        System.out.println("Name: " + name);

        int health = new Random().nextInt(100);
        System.out.println("Health: " + health);

        int day = new Random().nextInt(150);
        int days = -day;
        String date = new SimpleDateFormat("dd MM yyyy").format(Calendar.getInstance().getTime());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
        Calendar calendar = Calendar.getInstance();
        date = getDate(days, date, dateFormat, calendar);
        System.out.println("Date: " + date);
        databaseServer.addItemToBackPack(new Weapon(-1, level, attackStyle, name, health, date), userId);
        return getBackPackItems(userId);
    }

    @Override
    public boolean deleteItemFromBackPack(Item item, int userId)
    {
        return databaseServer.deleteItemFromBackPack(item, userId);
    }

    @Override
    public ArrayList<MarketOffer> getMarketOffers(int userId)
    {
        return databaseServer.getMarketOffers(userId);
    }

    @Override
    public boolean cancelOffer(MarketOffer offer)
    {
        return databaseServer.cancelOffer(offer);
    }

    @Override
    public ArrayList<MarketOffer> getSearchOffers(String searchQuery, int userId)
    {
        return databaseServer.getSearchOffers(searchQuery, userId);
    }

    @Override
    public boolean buyItem(MarketOffer marketOffer, int buyerId)
    {
        return databaseServer.buyItem(marketOffer,buyerId);
    }

    private String getDate(int days, String date, SimpleDateFormat dateFormat, Calendar calendar)
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

    private void calculateDatePrice(Item item)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
        long difference;
        int defaultPrice = DATE_MULTIPLIER * item.getAttackStyle().getValue() * item.getItemLevel();
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

    private void calculateAverageMarketSellingItemPrice(Item item)
    {
        int totalPrices = item.getPrice();
        int pricesCount = 1;
        for (MarketOffer offer : databaseServer.getSellingOffers())
        {
            if (offer.getItem().getItemLevel() > (item.getItemLevel() - item.getItemLevel() * 0.1) && offer.getItem().getItemLevel() < (item.getItemLevel() + item.getItemLevel() * 0.1))//10 percent
            {
                totalPrices += offer.getPrice();
                pricesCount++;
            }
        }
        if (totalPrices != item.getPrice()) item.setPrice(totalPrices / pricesCount);
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
        defaultPrice += (int)(item.getItemLevel() * item.getAttackStyle().getValue() * item.getAttackStyle().getValue() * 0.75);//75 percent
        item.setPrice(item.getPrice() + defaultPrice);
    }

    private boolean canPlayerWieldItem(User user, Item item)
    {
        return user.getLevel() >= ((item.getItemLevel()) / HALF);
    }
}