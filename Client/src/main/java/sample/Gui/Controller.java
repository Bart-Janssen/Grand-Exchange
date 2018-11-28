package sample.Gui;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Factory.ClientFactory;
import sample.Logic.IGrandExchangeReceiveLogic;
import sample.Logic.IGrandExchangeSendLogic;
import sample.Models.WebSocketType;

public abstract class Controller
{
    private IGrandExchangeSendLogic sendLogic = ClientFactory.getInstance().makeNewGrandExchangeSendLogic(WebSocketType.WEBSOCKETSERVER);
    private IGrandExchangeReceiveLogic receiveLogic = ClientFactory.getInstance().makeNewGrandExchangeReceiveLogic(WebSocketType.WEBSOCKETSERVER);
    private static Stage stage;

    IGrandExchangeSendLogic getSendLogic()
    {
        return sendLogic;
    }

    IGrandExchangeReceiveLogic getReceiveLogic()
    {
        return receiveLogic;
    }

    void openForm(final Stage oldForm, final String fxml, final String title, int width, int height)
    {
        stage = oldForm;
        Platform.runLater(() ->
        {
            try
            {
                Controller.stage.setScene(new Scene(FXMLLoader.load(Main.class.getClassLoader().getResource(fxml + ".fxml")), width, height));
                stage.setTitle(title);
                stage.show();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });
    }
}