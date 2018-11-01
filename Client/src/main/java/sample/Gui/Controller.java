package sample.Gui;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import sample.Factory.ClientFactory;
import sample.Logic.IGrandExchangeLogic;
import sample.Models.UserSession;
import sample.Models.WebSocketType;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import java.net.URI;

public class Controller implements IGui
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
        logic.login(textFieldUsername.getText(), textFieldPassword.getText());
    }

    public void textFieldUsername_KeyPress(KeyEvent keyEvent)
    {
        if (keyEvent.getCode() == KeyCode.ENTER)
        {
            logic.login(textFieldUsername.getText(), textFieldPassword.getText());
        }
    }

    public void textFieldPassword_KeyPress(KeyEvent keyEvent)
    {
        if (keyEvent.getCode() == KeyCode.ENTER)
        {
            logic.login(textFieldUsername.getText(), textFieldPassword.getText());
        }
    }
}