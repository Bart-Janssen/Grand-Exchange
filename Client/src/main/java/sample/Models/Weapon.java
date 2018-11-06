package sample.Models;

public class Weapon extends Item
{
    private int damage;

    public Weapon(int itemLevel, int damage, AttackStyle attackStyle)
    {
        super(itemLevel, attackStyle);
        this.damage = damage;
    }

    public int getDamage()
    {
        return damage;
    }
}