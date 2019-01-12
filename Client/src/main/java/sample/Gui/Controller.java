package sample.Gui;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Factory.ClientFactory;
import sample.Logic.ICalculateLogic;
import sample.Logic.IGrandExchangeReceiveLogic;
import sample.Logic.IGrandExchangeSendLogic;
import sample.Models.Logger;
import sample.Models.User;
import sample.Models.WebSocketType;

import java.util.ArrayList;

public abstract class Controller
{
    private IGrandExchangeSendLogic sendLogic = ClientFactory.getInstance().makeNewGrandExchangeSendLogic(WebSocketType.WEB_SOCKET_SERVER);
    private IGrandExchangeReceiveLogic receiveLogic = ClientFactory.getInstance().makeNewGrandExchangeReceiveLogic(WebSocketType.WEB_SOCKET_SERVER);
    private ICalculateLogic calculateLogic = ClientFactory.getInstance().makeNewCalculateLogic();
    private static Stage stage;
    private static ArrayList<String> messages = new ArrayList<>();
    private static User user;

    IGrandExchangeSendLogic getSendLogic()
    {
        return sendLogic;
    }

    IGrandExchangeReceiveLogic getReceiveLogic()
    {
        return receiveLogic;
    }

    ICalculateLogic getCalculateLogic()
    {
        return calculateLogic;
    }

    void openForm(final Stage oldForm, final String fxml)
    {
        stage = oldForm;
        Platform.runLater(() ->
        {
            try
            {
                Controller.stage.setScene(new Scene(FXMLLoader.load(Main.class.getClassLoader().getResource(fxml + ".fxml"))));
                stage.setTitle(fxml);
                stage.show();
            }
            catch (Exception e)
            {
                new Logger().log(e);
            }
        });
    }

    public void appendChat(String message)
    {
        if (message != null) Controller.messages.add(getCalculateLogic().makeMessageWithDate(message));
    }

    ArrayList<String> getMessages()
    {
        return messages;
    }

    void clearMessages()
    {
        messages.clear();
    }

    public void setUser(User user)
    {
        Controller.user = user;
    }

    User getUser()
    {
        return user;
    }
}