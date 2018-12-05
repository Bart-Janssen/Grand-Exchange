package sample.Models;

import java.util.ArrayList;

public class WebSocketMessage
{
    private MessageType operation;
    private String message;
    private User user;
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
}