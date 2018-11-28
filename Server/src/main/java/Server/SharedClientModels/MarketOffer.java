package Server.SharedClientModels;

public class MarketOffer
{
    private int price;
    private Item item;
    private User user;
    private MarketOfferType type;

    public MarketOffer(int price, Item item, MarketOfferType type, User user)
    {
        this.price = price;
        this.item = item;
        this.user = user;
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

    public User getUser()
    {
        return user;
    }
}