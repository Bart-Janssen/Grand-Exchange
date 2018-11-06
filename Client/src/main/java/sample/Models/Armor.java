package sample.Models;

public class Armor extends Item
{
    private int defence;

    public Armor(int itemLevel, int defence, AttackStyle attackStyle)
    {
        super(itemLevel, attackStyle);
        this.defence = defence;
    }

    public int getDefence()
    {
        return defence;
    }
}