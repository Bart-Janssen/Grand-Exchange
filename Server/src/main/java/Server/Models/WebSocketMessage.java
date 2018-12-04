package Server.Models;

import Server.SharedClientModels.Item;
import Server.SharedClientModels.MessageType;
import Server.SharedClientModels.User;

import java.util.ArrayList;

public class WebSocketMessage
{
    private MessageType operation;
    private String message;
    private User user;
    private Item item;
    private ArrayList<Item> items = new ArrayList<>();

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

    public Item getItem()
    {
        return item;
    }

    public void setItem(Item item)
    {
        this.item = item;
    }

    public ArrayList<Item> getItems()
    {
        return items;
    }

    public void setItems(ArrayList<Item> items)
    {
        this.items = items;
    }
}