package Server.SharedClientModels;

public class MarketOffer
{
    private int price;
    private Item item;
    private User user;

    public MarketOffer(int price, Item item, User user)
    {
        this.price = price;
        this.item = item;
        this.user = user;
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