package sample.Gui;

import sample.Factory.ClientFactory;
import sample.Logic.IGrandExchangeLogic;
import sample.Models.UserSession;
import sample.Models.WebSocketType;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import java.net.URI;

public class Controller implements IGui
{
    private IGrandExchangeLogic logic = ClientFactory.getInstance().makeNewIGrandExchangeLogic(this, WebSocketType.WEBSOCKETSERVER);

    public Controller()
    {
        connectToServer("ws://localhost:6666/grandExchangeServer/");
    }

    private void connectToServer(String server)
    {
        WebSocketContainer container;
        try
        {
            container = ContainerProvider.getWebSocketContainer();
            UserSession.getInstance().setSession(container.connectToServer(logic, URI.create(server)));
        }
        catch (Exception e)
        {
            e.printStackTrace(System.err);
        }
        logic.login("bart", "secret password");
    }
}