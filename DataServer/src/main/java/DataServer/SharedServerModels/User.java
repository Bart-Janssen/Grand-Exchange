package DataServer.SharedServerModels;

public class User
{
    private String username;
    private String password;
    private boolean loggedIn = false;
    private int level;
    private int id;

    public User(){}

    public User(String username, String password, int level, int id)
    {
        this.username = username;
        this.password = password;
        this.level = level;
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public boolean isLoggedIn()
    {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn)
    {
        this.loggedIn = loggedIn;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getLevel()
    {
        return level;
    }
}