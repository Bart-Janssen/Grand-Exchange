package sample.Gui;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RegisterController extends Controller implements IRegisterGui
{
    public TextField textFieldUsername;
    public PasswordField textFieldPassword;
    public PasswordField textFieldReTypePassword;
    public Label labelRegisterFailed;
    public GridPane registerForm;

    public RegisterController()
    {
        super.getReceiveLogic().setController(this);
    }

    public void buttonRegister_Click()
    {
        register();
    }

    public void textFieldUsername_KeyPress(KeyEvent keyEvent)
    {
        if (keyEvent.getCode() == KeyCode.ENTER)  register();
    }

    public void textFieldPassword_KeyPress(KeyEvent keyEvent)
    {
        if (keyEvent.getCode() == KeyCode.ENTER) register();
    }

    public void textFieldReTypePassword_KeyPress(KeyEvent keyEvent)
    {
        if (keyEvent.getCode() == KeyCode.ENTER) register();
    }

    private void register()
    {
        if (textFieldPassword.getLength() < 8)
        {
            labelRegisterFailed.setVisible(true);
            labelRegisterFailed.setText("Password need to have at least 8 characters");
            return;
        }
        if (!textFieldPassword.getText().equals(textFieldReTypePassword.getText()))
        {
            labelRegisterFailed.setVisible(true);
            labelRegisterFailed.setText("Passwords does not match");
            return;
        }
        super.getSendLogic().register(textFieldUsername.getText(), textFieldPassword.getText());
    }

    public void buttonBack_Click()
    {
        super.openForm(((Stage)registerForm.getScene().getWindow()),"Login");
    }

    @Override
    public void registeringEvent(String message)
    {
        if (message.equals("Registered successfully"))
        {
            super.openForm(((Stage)registerForm.getScene().getWindow()),"Login");
            return;
        }
        Platform.runLater(() ->
        {
            labelRegisterFailed.setVisible(true);
            labelRegisterFailed.setText(message);
        });
    }
}