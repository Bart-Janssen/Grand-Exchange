package sample.Logic;

import sample.Models.*;
import sample.WebSocketConnection.IConnection;

import java.util.Timer;
import java.util.TimerTask;

public class GrandExchangeSendLogic implements IGrandExchangeSendLogic
{
    private IConnection connection;
    private int heartBeatCountDown = 240;


    public GrandExchangeSendLogic(IConnection webSocketConnection)
    {
        this.connection = webSocketConnection;
        startHeartbeatTimer();
    }

    private void startHeartbeatTimer()
    {

        Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                System.out.println(heartBeatCountDown);
                heartBeatCountDown--;
                if (heartBeatCountDown <= 0)
                {
                    connection.sentHeartBeat();
                    heartBeatCountDown = 240;
                }
            }
        }, 1000, 1000);
    }

    @Override
    public void login(String username, String password)
    {
        connection.login(new User(username, password));
    }

    @Override
    public void sellItem(int price, Item item)
    {
        connection.sellItem(price, item);
    }

    @Override
    public void calculateItemPrice(Item item)
    {
        connection.calculateItemPrice(item);
    }

    @Override
    public void getBackPackItems()
    {
        connection.getBackPackItems();
    }

    @Override
    public void generateNewWeapon()
    {
        connection.generateNewWeapon();
    }
}