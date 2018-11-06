package sample.Gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.Factory.ClientFactory;
import sample.Logic.IGrandExchangeLogic;
import sample.Models.UserSession;
import sample.Models.WebSocketType;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.util.Observable;

public class Controller extends Observable implements IGui
{
    public TextField textFieldUsername;
    public TextField textFieldPassword;

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
    }

    public void buttonLogin_Click(ActionEvent actionEvent)
    {
        openSecondScene(actionEvent);
       // logic.login(textFieldUsername.getText(), textFieldPassword.getText());
    }

    public void textFieldUsername_KeyPress(KeyEvent keyEvent)
    {
        if (keyEvent.getCode() == KeyCode.ENTER)
        {


            //logic.login(textFieldUsername.getText(), textFieldPassword.getText());
            //notifyObservers();
        }
    }

    public void textFieldPassword_KeyPress(KeyEvent keyEvent)
    {
        if (keyEvent.getCode() == KeyCode.ENTER)
        {
            logic.login(textFieldUsername.getText(), textFieldPassword.getText());
        }
    }

    private Scene secondScene;

    public void setSecondScene(Scene scene) {
        secondScene = scene;
    }

    public void openSecondScene(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(secondScene);
    }
}