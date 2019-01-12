package sample.Gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Factory.ClientFactory;
import sample.Logic.Connector;
import sample.Logic.IGrandExchangeReceiveLogic;
import sample.Models.Logger;
import sample.Models.UserSession;
import sample.Models.WebSocketType;
import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;

public class Main extends Application
{
    private static IGrandExchangeReceiveLogic logic = ClientFactory.getInstance().makeNewGrandExchangeReceiveLogic(WebSocketType.WEB_SOCKET_SERVER);
    private static Stage stage;
    private static String server = "ws://localhost:6667/grandExchangeServer/";
    private static boolean connectionFailedFormRunning = false;
    private static boolean connected = false;

    @Override
    public void start(Stage primaryStage)
    {
        startAndConnectApplication(primaryStage, server);
    }

    private static void startAndConnectApplication(Stage stage, String server)
    {
        stage.setTitle("Grand Exchange");
        Main.stage = stage;
        stage.setResizable(false);
        try
        {
            stage.setScene(new Scene(FXMLLoader.load(Main.class.getClassLoader().getResource("Login.fxml")), 300, 300));
            if (!connected)
            {
                WebSocketContainer container = ContainerProvider.getWebSocketContainer();
                UserSession.getInstance().setSession(container.connectToServer(logic, URI.create(server)));
            }
            stage.show();
            return;
        }
        catch (Exception e)
        {
            startReconnecting(server);
        }
        if (!connectionFailedFormRunning) callFailedConnectingForm(stage);
    }

    private static void callFailedConnectingForm(Stage stage)
    {
        try
        {
            stage.setScene(new Scene(FXMLLoader.load(Main.class.getClassLoader().getResource("ConnectionFailure.fxml")), 300, 300));
        }
        catch (IOException e)
        {
            new Logger().log(e);
        }
        stage.show();
        Main.stage = stage;
        connectionFailedFormRunning = true;
    }


    private static void startReconnecting(String server)
    {
        try
        {
            Connector connector = Connector.getInstance();
            connector.setSever(logic, server);
        }
        catch (Exception ex)
        {
            new Logger().log(ex);
        }
    }

    static void restart()
    {
        connected = true;
        startAndConnectApplication(stage, server);
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
}