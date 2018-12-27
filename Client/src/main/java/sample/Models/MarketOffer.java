package sample.Models;

public class MarketOffer
{
    private int id;
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

    public void setPrice(int price)
    {
        this.price = price;
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
}