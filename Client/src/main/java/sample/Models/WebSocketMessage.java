package sample.Models;

public class WebSocketMessage
{
    private MessageType operation;
    private String message;
    private User user;
    private Item item;

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

    public void setItem(Item item)
    {
        this.item = item;
    }
}