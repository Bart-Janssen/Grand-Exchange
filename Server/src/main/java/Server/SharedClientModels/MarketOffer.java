package Server.SharedClientModels;

public class MarketOffer
{
    private int id;
    private int userId;
    private int price;
    private Item item;
    private MarketOfferType type;

    public MarketOffer(int id, int price, Item item, MarketOfferType type)
    {
        this.id = id;
        this.price = price;
        this.item = item;
        this.type = type;
    }

    public int getPrice()
    {
        return price;
    }

    public Item getItem()
    {
        return item;
    }

    public MarketOfferType getType()
    {
        return type;
    }

    public int getId()
    {
        return id;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }
}