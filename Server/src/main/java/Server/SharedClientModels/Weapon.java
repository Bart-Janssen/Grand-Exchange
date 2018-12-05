package Server.SharedClientModels;

public class Weapon extends Item
{
    public Weapon(int itemLevel, AttackStyle attackStyle, String name)
    {
        super(itemLevel, attackStyle, name);
    }

    public Weapon(int level, AttackStyle attackStyle, String name, int health, String date)
    {
        super(level, attackStyle, name, health, date);
    }
}