package Server.ServerLogic;

import Server.DataServer.IGrandExchangeDatabaseServer;
import Server.Models.Logger;
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
    private static final int FULL_WEAPON_HEALTH = 100;
    private static final int HALF = 2;
    private static final int DATE_MULTIPLIER = 15;
    private static final int START_COINS_AMOUNT = 1000;
    private static final int START_LEVEL = 1;
    private static final String DATE_FORMAT = "dd MM yyyy";

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
        new Logger().print("Wep price: " + item.getPrice());

        calculateDatePrice(item);
        new Logger().print("date price: " + item.getPrice());

        calculateWeaponHealth(item);
        new Logger().print("Wep health: " + item.getPrice());

        calculateAverageMarketSellingItemPrice(item);
        new Logger().print("MarketSelling price: " + item.getPrice());

        return item.getPrice();
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
        new Logger().print("Attack style enum: " + attackStyle);

        int level = new Random().nextInt(200) + 1;
        new Logger().print("level: " + level);

        String name = names.get(typeIndex)[nameIndex];
        new Logger().print("Name: " + name);

        int health = new Random().nextInt(FULL_WEAPON_HEALTH);
        new Logger().print("Health: " + health);

        int day = new Random().nextInt(150);
        int days = -day;
        String date = new SimpleDateFormat(DATE_FORMAT).format(Calendar.getInstance().getTime());

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        Calendar calendar = Calendar.getInstance();
        date = getDate(days, date, dateFormat, calendar);
        new Logger().print("Date: " + date);
        databaseServer.addItemToBackPack(new Armor(-1, level, attackStyle, name, health, date), userId);
        return getBackPackItems(userId);
    }

    private ArrayList<Item> generateNewWeapon(int userId)
    {
        int index = new Random().nextInt(AttackStyle.values().length);
        AttackStyle attackStyle = AttackStyle.values()[index];
        new Logger().print("Attack style enum: " + attackStyle);

        int level = new Random().nextInt(200) + 1;
        new Logger().print("level: " + level);

        String[] names = {"Bow", "Sword", "Staff"};
        String name = names[index];
        new Logger().print("Name: " + name);

        int health = new Random().nextInt(FULL_WEAPON_HEALTH);
        new Logger().print("Health: " + health);

        int day = new Random().nextInt(150);
        int days = -day;
        String date = new SimpleDateFormat(DATE_FORMAT).format(Calendar.getInstance().getTime());

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        Calendar calendar = Calendar.getInstance();
        date = getDate(days, date, dateFormat, calendar);
        new Logger().print("Date: " + date);
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

    @Override
    public String register(User user)
    {
        user.setLevel(START_LEVEL);
        user.setCoins(START_COINS_AMOUNT);
        return databaseServer.register(user);
    }

    @Override
    public int getUserCoins(int id)
    {
        return databaseServer.getUserCoins(id);
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
            new Logger().log(e);
        }
        return date;
    }

    private void calculateDatePrice(Item item)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        long difference;
        int defaultPrice = DATE_MULTIPLIER * item.getAttackStyle().getValue() * item.getItemLevel();
        try
        {
            difference = TimeUnit.DAYS.convert(dateFormat.parse(new SimpleDateFormat(DATE_FORMAT).format(Calendar.getInstance().getTime())).getTime() - dateFormat.parse(item.getObtainDate()).getTime(), TimeUnit.MILLISECONDS);
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
            new Logger().log(e);
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
        if (item.getItemHealth() == FULL_WEAPON_HEALTH) return;
        item.setPrice((item.getPrice() / FULL_WEAPON_HEALTH) * item.getItemHealth());
    }

    private void calculateWeaponPrice(User user, Item item)
    {
        int defaultPrice = user.getLevel() + item.getItemLevel();
        if (canPlayerWieldItem(user, item))
        {
            defaultPrice += item.getItemLevel() * item.getAttackStyle().getValue() * item.getAttackStyle().getValue();
            item.setPrice(item.getPrice() + defaultPrice);
            return;
        }
        defaultPrice += (int)(item.getItemLevel() * item.getAttackStyle().getValue() * item.getAttackStyle().getValue() * 0.75);//75 percent
        item.setPrice(item.getPrice() + defaultPrice);
    }

    private boolean canPlayerWieldItem(User user, Item item)
    {
        return user.getLevel() >= ((item.getItemLevel()) / HALF);
    }
}