package Server.SharedClientModels;

public class Armor extends Item
{
    public Armor(int id, int itemLevel, AttackStyle attackStyle, String name)
    {
        super(id, itemLevel, attackStyle, name);
    }

    public Armor(int id, int level, AttackStyle attackStyle, String name, int health, String date)
    {
        super(id, level, attackStyle, name, health, date);
    }
}