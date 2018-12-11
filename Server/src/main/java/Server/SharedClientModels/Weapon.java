package Server.SharedClientModels;

public class Weapon extends Item
{
    public Weapon(int id, int itemLevel, AttackStyle attackStyle, String name)
    {
        super(id, itemLevel, attackStyle, name);
    }

    public Weapon(int id, int level, AttackStyle attackStyle, String name, int health, String date)
    {
        super(id, level, attackStyle, name, health, date);
    }
}