package sample.Gui;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.Factory.ClientFactory;
import sample.Logic.IGrandExchangeReceiveLogic;
import sample.Logic.IGrandExchangeSendLogic;
import sample.Models.WebSocketType;

public abstract class Gui
{
    private IGrandExchangeSendLogic sendLogic = ClientFactory.getInstance().makeNewGrandExchangeSendLogic(WebSocketType.WEBSOCKETSERVER);
    private IGrandExchangeReceiveLogic receiveLogic = ClientFactory.getInstance().makeNewGrandExchangeReceiveLogic(WebSocketType.WEBSOCKETSERVER);

    IGrandExchangeSendLogic getSendLogic()
    {
        return sendLogic;
    }

    IGrandExchangeReceiveLogic getReceiveLogic()
    {
        return receiveLogic;
    }

    void openForm(final Stage oldForm, final String fxml, String title)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxml + ".fxml"));
            Parent root = loader.load();
            Stage newForm = new Stage();
            newForm.setTitle(title);
            newForm.setScene(new Scene(root));
            newForm.setOnCloseRequest(new EventHandler<WindowEvent>()
            {
                @Override
                public void handle(WindowEvent event)
                {
                    if (!fxml.equals("Game")) openForm(oldForm,"Game", "Game");
                }
            });
            newForm.show();
            oldForm.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}