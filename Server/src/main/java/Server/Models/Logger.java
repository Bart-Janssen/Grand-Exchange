package Server.Models;

public class Logger
{
    public void log(Exception e)
    {
        e.printStackTrace();
    }

    public void print(String message)
    {
        System.out.println(message);
    }
}