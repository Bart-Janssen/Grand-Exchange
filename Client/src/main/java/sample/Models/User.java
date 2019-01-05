package sample.Models;

public class User
{
    private String username;
    private String password;
    private boolean loggedIn = false;
    private int level;
    private int id;
    private int coins;


    /*public User(String username, String password, int level, int id)
    {
        this.username = username;
        this.password = password;
        this.level = level;
        this.id = id;
    }*/

    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public boolean isLoggedIn()
    {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn)
    {
        this.loggedIn = loggedIn;
    }

    public int getLevel()
    {
        return level;
    }

    public int getId()
    {
        return id;
    }

    public int getCoins()
    {
        return coins;
    }
}