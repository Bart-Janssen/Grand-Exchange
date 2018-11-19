package sample.Logic;

import sample.Models.UserSession;
import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import java.net.URI;
import java.util.Observable;

public class Connector extends Observable implements Runnable
{
    private IGrandExchangeReceiveLogic logic;
    private String server;
    private static Connector instance = null;

    private Connector(){}

    public static Connector getInstance()
    {
        if (instance == null) instance = new Connector();
        return instance;
    }

    public void setSever(IGrandExchangeReceiveLogic logic, String server)
    {
        this.logic = logic;
        this.server = server;
    }

    @Override
    public void run()
    {
        try
        {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            UserSession.getInstance().setSession(container.connectToServer(logic, URI.create(server)));
        }
        catch (Exception ex)
        {
            setChanged();
            notifyObservers(false);
            return;
        }
        setChanged();
        notifyObservers(true);
    }
}