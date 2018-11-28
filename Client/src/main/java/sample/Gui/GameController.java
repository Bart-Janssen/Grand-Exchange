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
        Platform.runLater(() -> System.out.println(ding));
    }

    public void buttonOpenBackpack_Click(ActionEvent actionEvent)
    {
        super.openForm(((Stage)gameForm.getScene().getWindow()),"Backpack", "BackPack", 300 , 300);
    }

    public void buttonOpenMarket_Click(ActionEvent actionEvent)
    {
        super.openForm(((Stage)gameForm.getScene().getWindow()),"Market", "Market", 800, 300);
    }
}