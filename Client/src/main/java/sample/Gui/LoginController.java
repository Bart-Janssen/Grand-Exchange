package sample.Gui;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginController extends Controller implements ILoginGui
{
    public GridPane loginForm;
    public TextField textFieldUsername;
    public TextField textFieldPassword;
    public Label labelLoginFailed;

    public LoginController()
    {
        super.getReceiveLogic().setController(this);
    }

    public void buttonLogin_Click()
    {
        login();
    }

    public void textFieldUsername_KeyPress(KeyEvent keyEvent)
    {
        if (keyEvent.getCode() == KeyCode.ENTER) login();
    }

    private void login()
    {
        if (!textFieldUsername.getText().isEmpty() || !textFieldPassword.getText().isEmpty())
        {
            super.getSendLogic().login(textFieldUsername.getText(), textFieldPassword.getText());
            labelLoginFailed.setVisible(false);
            return;
        }
        labelLoginFailed.setText("Fields may not be empty");
        labelLoginFailed.setVisible(true);
    }

    public void textFieldPassword_KeyPress(KeyEvent keyEvent)
    {
        if (keyEvent.getCode() == KeyCode.ENTER) login();
    }

    @Override
    public void callGameGui()
    {
        super.openForm(((Stage)loginForm.getScene().getWindow()),"Game");
    }

    @Override
    public void loginFailed()
    {
        Platform.runLater(() ->
        {
            labelLoginFailed.setText("Login failed");
            labelLoginFailed.setVisible(true);
        });
    }

    public void labelRegister_Click()
    {
        super.openForm(((Stage)loginForm.getScene().getWindow()),"Register");
    }
}