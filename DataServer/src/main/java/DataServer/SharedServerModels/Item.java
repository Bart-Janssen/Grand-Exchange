package DataServer.SharedServerModels;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Item
{
    private int id;
    private String name;
    private int price;
    private String obtainDate;
    private int itemLevel;
    private int itemHealth;
    private AttackStyle attackStyle;
    private String iconPath;

    public Item(int id, int itemLevel, AttackStyle attackStyle, String name)
    {
        this.id = id;
        this.obtainDate = new SimpleDateFormat("dd MM yyyy").format(Calendar.getInstance().getTime());
        this.attackStyle = attackStyle;
        this.itemLevel = itemLevel;
        this.name = name;
        this.iconPath = name + ".png";
        this.itemHealth = 100;
    }

    public Item(int id, int itemLevel, AttackStyle attackStyle, String name, int health, String obtainDate)
    {
        this.id = id;
        this.attackStyle = attackStyle;
        this.itemLevel = itemLevel;
        this.name = name;
        this.itemHealth = health;
        this.obtainDate = obtainDate;
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

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }
}