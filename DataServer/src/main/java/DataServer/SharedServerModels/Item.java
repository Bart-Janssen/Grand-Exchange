package DataServer.SharedServerModels;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Item
{
    private String name;
    private int price;
    private String obtainDate;
    private int itemLevel;
    private int itemHealth;
    private AttackStyle attackStyle;
    private String iconPath;

    public Item(int itemLevel, AttackStyle attackStyle, String name)
    {
        this.obtainDate = new SimpleDateFormat("dd MM yyyy").format(Calendar.getInstance().getTime());
        this.attackStyle = attackStyle;
        this.itemLevel = itemLevel;
        this.name = name;
        this.iconPath = name + ".png";
        this.itemHealth = 100;
    }

    public Item(int itemLevel, AttackStyle attackStyle, String name, int health, Date obtainDate)
    {
        this.attackStyle = attackStyle;
        this.itemLevel = itemLevel;
        this.name = name;
        this.itemHealth = health;
        this.obtainDate = new SimpleDateFormat("dd MM yyyy").format(obtainDate);
        this.iconPath = name + ".png";
    }

    public int getPrice()
    {
        return price;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    public String getObtainDate()
    {
        return obtainDate;
    }

    public void setObtainDate(String date)
    {
        this.obtainDate = date;
    }

    public int getItemLevel()
    {
        return itemLevel;
    }

    public int getItemHealth()
    {
        return itemHealth;
    }

    public void subtractItemHealth(int amount)
    {
        if (amount > itemHealth) this.itemHealth = 0;
        if (itemHealth > 0) this.itemHealth -= amount;
    }

    public AttackStyle getAttackStyle()
    {
        return attackStyle;
    }

    public String getName()
    {
        return name;
    }

    public String getIconPath()
    {
        return iconPath;
    }
}