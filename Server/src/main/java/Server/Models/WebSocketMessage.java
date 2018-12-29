package Server.Models;

import Server.SharedClientModels.Item;
import Server.SharedClientModels.MarketOffer;
import Server.SharedClientModels.MessageType;
import Server.SharedClientModels.User;

import java.util.ArrayList;

public class WebSocketMessage
{
    private MessageType operation;
    private String message;
    private User user;
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<MarketOffer> offers = new ArrayList<>();

    public MessageType getOperation()
    {
        return operation;
    }

    public void setOperation(MessageType operation)
    {
        this.operation = operation;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public ArrayList<Item> getItems()
    {
        return items;
    }

    public void setItems(ArrayList<Item> items)
    {
        this.items = items;
    }

    public void addItem(Item item)
    {
        this.items.add(item);
    }

    public void setMarketOffers(ArrayList<MarketOffer> offers)
    {
        this.offers = offers;
    }

    public ArrayList<MarketOffer> getOffers()
    {
        return offers;
    }

    public void addMarketOffer(MarketOffer offer)
    {
        this.offers.add(offer);
    }
}