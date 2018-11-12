package sample.Gui;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginController extends Gui implements ILoginGui
{
    public GridPane loginForm;
    public TextField textFieldUsername;
    public TextField textFieldPassword;
    public Label labelLoginFailed;

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
        super.openForm(((Stage)loginForm.getScene().getWindow()),"Game","Game");
    }

    @Override
    public void loginFailed()
    {
        labelLoginFailed.setVisible(true);
    }

    public void labelRegister_Click(MouseEvent mouseEvent)
    {
        System.out.println("Register clicked");
        //TODO: register form aanmaken
    }
}