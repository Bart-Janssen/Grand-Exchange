package sample.Logic;

import sample.Models.*;
import sample.WebSocketConnection.IConnection;
import java.util.Timer;
import java.util.TimerTask;

public class GrandExchangeSendLogic implements IGrandExchangeSendLogic
{
    private IConnection connection;
    private static int heartBeatCountDown = 240;
    private static Timer heartBeatTimer = null;

    public GrandExchangeSendLogic(IConnection webSocketConnection)
    {
        this.connection = webSocketConnection;
        startHeartbeatTimer();
        heartBeatCountDown = 240;
    }

    private void startHeartbeatTimer()
    {
        if (heartBeatTimer == null)
        {
            heartBeatTimer = new Timer();
            heartBeatTimer.schedule(new TimerTask()
            {
                @Override
                public void run()
                {
                    heartBeatCountDown--;
                    System.out.println("heartbeat time: " + heartBeatCountDown);
                    if (heartBeatCountDown <= 0)
                    {
                        connection.sentHeartBeat();
                        heartBeatCountDown = 240;
                    }
                }
            }, 1000, 1000);
        }
    }

    @Override
    public void login(String username, String password)
    {
        connection.login(new User(username, password));
    }

    @Override
    public void sellItem(MarketOffer offer)
    {
        connection.sellItem(offer);
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

    @Override
    public void deleteItemFromBackPack(Item item)
    {
        connection.deleteItemFromBackPack(item);
    }

    @Override
    public void logout()
    {
        connection.logout();
    }

    @Override
    public void getMarketOffers()
    {
        connection.getMarketOffers();
    }

    @Override
    public void cancelOffer(MarketOffer offer)
    {
        connection.cancelOffer(offer);
    }

    @Override
    public void getMarketOffersCount()
    {
        connection.getMarketOffersCount();
    }

    @Override
    public void getSearchOffers(String searchQuery)
    {
        connection.getSearchOffers(searchQuery);
    }

    @Override
    public void buyItem(MarketOffer offer)
    {
        connection.buyItem(offer);
    }

    @Override
    public void register(String username, String password)
    {
        connection.register(new User(username, password));
    }
}