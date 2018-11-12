package Server.SharedClientModels;

public class User
{
    private String username;
    private String password;
    private boolean loggedIn = false;
    private int level;

    public User(String username, String password, int level)
    {
        this.username = username;
        this.password = password;
        this.level = level;
    }

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
}