package sample.Models;

public class Weapon extends Item
{
    public Weapon(int itemLevel, int damage, AttackStyle attackStyle, String name)
    {
        super(itemLevel, attackStyle, name);
    }
}