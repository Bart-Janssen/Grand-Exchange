package sample.Gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GameController extends Controller implements IGameGui
{
    public GridPane gameForm;

    public GameController()
    {
        super.getReceiveLogic().setController(this);
    }

    @Override
    public void MBOX(final String ding)//TODO:
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                System.out.println(ding);
            }
        });
    }

    public void buttonOpenBackpack_Click(ActionEvent actionEvent)
    {
        super.openForm(((Stage)gameForm.getScene().getWindow()),"Backpack", "BackPack");
    }

    public void buttonOpenMarket_Click(ActionEvent actionEvent)
    {
        super.openForm(((Stage)gameForm.getScene().getWindow()),"Market", "Market");
    }
}