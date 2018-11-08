package sample.Gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Factory.ClientFactory;
import sample.Logic.IGrandExchangeReceiveLogic;
import sample.Models.UserSession;
import sample.Models.WebSocketType;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import java.net.URI;
import java.util.Observable;
import java.util.Observer;

public class Main extends Application implements Observer
{
    private IGrandExchangeReceiveLogic logic = ClientFactory.getInstance().makeNewGrandExchangeReceiveLogic(WebSocketType.WEBSOCKETSERVER);

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        connectToServer("ws://localhost:6666/grandExchangeServer/");
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Login.fxml"));
        primaryStage.setTitle("Grand Exchange");
        primaryStage.setScene(new Scene(root, 300, 300));
        primaryStage.show();
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
    }

    @Override
    public void stop()
    {
        System.exit(-1);
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void update(Observable o, Object arg)
    {
    }
}