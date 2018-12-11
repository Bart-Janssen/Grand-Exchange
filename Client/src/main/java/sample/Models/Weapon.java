package sample.Models;

public class Weapon extends Item
{
    public Weapon(int id, int itemLevel, int damage, AttackStyle attackStyle, String name)
    {
        super(id, itemLevel, attackStyle, name);
    }
}