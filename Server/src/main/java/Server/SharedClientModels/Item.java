package Server.SharedClientModels;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Item
{
    private int price;
    private String obtainDate;
    private int itemLevel;
    private int damagedState = 0;
    private AttackStyle attackStyle;

    public Item(int itemLevel, AttackStyle attackStyle)
    {
        this.obtainDate = new SimpleDateFormat("dd MM yyyy").format(Calendar.getInstance().getTime());
        this.attackStyle = attackStyle;
        this.itemLevel = itemLevel;
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

    public int getDamagedState()
    {
        return damagedState;
    }

    public void setDamagedState(int damagedState)
    {
        this.damagedState = damagedState;
    }

    public AttackStyle getAttackStyle()
    {
        return attackStyle;
    }
}