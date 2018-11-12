package DataServer.Database;

import DataServer.SharedServerModels.User;

import java.util.HashMap;

public class HashMapDatabase implements IDatabaseConnection
{
    private HashMap<String, String> users = new HashMap<>();

    public HashMapDatabase()
    {
        users.put("bart", "bart");
        users.put("", "");
    }

    @Override
    public boolean login(User user)
    {
        try
        {
            return users.get(user.getUsername()).equals(user.getPassword());
        }
        catch (Exception ex)
        {
            System.out.println("User: " + user.getUsername() + " doesn't exist, cant login.");
        }
        return false;
    }

    @Override
    public void register(User user)
    {

    }
}