package Server.SharedClientModels;

public enum AttackStyle
{
    RANGED(60), MELEE(40), MAGIC(50);

    private final int value;

    AttackStyle(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }
}