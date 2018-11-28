package sample.Models;

public class MarketOffer
{
    private int price;
    private Item item;
    private MarketOfferType type;

    public MarketOffer(int price, Item item, MarketOfferType type)
    {
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
}