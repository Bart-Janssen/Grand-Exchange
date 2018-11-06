package sample.Logic;

import com.google.gson.Gson;
import sample.Gui.IGui;
import sample.Models.*;
import sample.WebSocketConnection.IWebSocketConnection;
import javax.websocket.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

@ClientEndpoint
public class GrandExchangeLogic implements IGrandExchangeLogic
{
    private IGui gui;
    private IWebSocketConnection webSocketConnection;

    public GrandExchangeLogic(IGui gui, IWebSocketConnection webSocketConnection)
    {
        this.gui = gui;
        this.webSocketConnection = webSocketConnection;
    }

    @OnOpen
    public void onWebSocketConnect()
    {
        System.out.println("[Connected]");
    }

    @OnMessage
    public void onWebSocketText(String message)
    {
        System.out.println("[Received]: " + message);
        handleServerMessage(message);
    }

    @OnClose
    public void onWebSocketClose(CloseReason reason)
    {
        System.out.println("[Closed]: " + reason);
    }

    @OnError
    public void onWebSocketError(Throwable cause)
    {
        System.out.println("[ERROR]: " + cause.getMessage());
    }

    private void handleServerMessage(String jsonMessage)
    {
        WebSocketMessage webSocketMessage;
        try
        {
            webSocketMessage = new Gson().fromJson(jsonMessage, WebSocketMessage.class);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return;
        }
        MessageType type = webSocketMessage.getOperation();
        switch (type)
        {
            case LOGIN:
                System.out.println(webSocketMessage.getMessage() + ", Logged in: " + webSocketMessage.getUser().isLoggedIn());
                break;
        }
    }

    @Override
    public void login(String username, String password)
    {
        webSocketConnection.login(new User(username, password));
    }

    public int calculateDateWeaponState(Item item)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
        long difference;
        try
        {
            difference = TimeUnit.DAYS.convert(dateFormat.parse(item.getObtainDate()).getTime() - dateFormat.parse(new SimpleDateFormat("dd MM yyyy").format(Calendar.getInstance().getTime())).getTime(), TimeUnit.MILLISECONDS);
            System.out.println(difference);
            if (difference <= 10) return 15 * item.getItemLevel();
            if (difference >= 150) return 0;
            return (15 * item.getItemLevel()) - (int)Math.floor((double)difference / 10) * item.getItemLevel();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int sellItem(Item item)
    {
        if (item.getDamagedState() == 100)
        {
            return 0;
        }
        int power = 0;
        if (item instanceof Armor)
        {
            power = ((Armor)item).getDefence();
        }
        return ((item.getItemLevel() * item.getDamagedState()) * 1000) + ((power * item.getItemLevel()) + ((item.getAttackStyle().getValue() * calculateDateWeaponState(item))));
    }
}