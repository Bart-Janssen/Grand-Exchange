package sample.Gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class LoginController extends Gui implements ILoginGui
{
    public TextField textFieldUsername;
    public TextField textFieldPassword;

    public LoginController()
    {
        super.getReceiveLogic().setGui(this);
    }

    public void buttonLogin_Click(ActionEvent actionEvent)
    {
        super.getSendLogic().login(textFieldUsername.getText(), textFieldPassword.getText());
    }

    public void textFieldUsername_KeyPress(KeyEvent keyEvent)
    {
        if (keyEvent.getCode() == KeyCode.ENTER)
        {
            super.getSendLogic().login(textFieldUsername.getText(), textFieldPassword.getText());
        }
    }

    public void textFieldPassword_KeyPress(KeyEvent keyEvent)
    {
        if (keyEvent.getCode() == KeyCode.ENTER)
        {
            super.getSendLogic().login(textFieldUsername.getText(), textFieldPassword.getText());
        }
    }

    @Override
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