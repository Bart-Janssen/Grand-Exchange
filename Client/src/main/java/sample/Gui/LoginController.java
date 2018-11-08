package sample.Gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sample.Factory.ClientFactory;
import sample.Logic.IGrandExchangeLogic;
import sample.Models.*;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import java.net.URI;

public class LoginController implements ILoginGui
{
    public TextField textFieldUsername;
    public TextField textFieldPassword;

    private IGrandExchangeLogic logic = ClientFactory.getInstance().makeNewIGrandExchangeLogic(this, WebSocketType.WEBSOCKETSERVER);

    public LoginController()
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

    public void callGameGui()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Option_Menu.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Exchange");
            stage.setScene(new Scene(root));
            stage.show();
            ((Stage)textFieldUsername.getScene().getWindow()).close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}