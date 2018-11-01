package sample.Models;

import javax.websocket.Session;

public class UserSession
{
    private Session session;
    private String chatMessage;
    private static UserSession instance = null;

    public Session getSession()
    {
        return session;
    }

    public void setSession(Session session)
    {
        this.session = session;
    }

    public static UserSession getInstance()
    {
        if (instance == null)instance = new UserSession();
        return instance;
    }

    public String getChatMessage()
    {
        return chatMessage;
    }

    public void setChatMessage(String chatMessage)
    {
        this.chatMessage = chatMessage;
    }
}