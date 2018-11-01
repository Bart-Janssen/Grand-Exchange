package DataServer.Database;

import DataServer.SharedServerModels.User;

import java.util.HashMap;

public class HashMapDatabase implements IDatabaseConnection
{
    private HashMap<String, String> users = new HashMap<>();

    public HashMapDatabase()
    {
        users.put("bart", "secret password");
    }

    @Override
    public boolean login(User user)
    {
        return users.get(user.getUsername()).equals(user.getPassword());
    }

    @Override
    public void register(User user)
    {

    }
}
