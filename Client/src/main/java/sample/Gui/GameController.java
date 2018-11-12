package sample.Gui;

import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GameController extends Gui implements IGameGui
{
    public GridPane gameForm;

    public GameController()
    {
        super.getReceiveLogic().setGui(this);
    }

    @Override
    public void MBOX(String ding)//TODO:
    {
        System.out.println(ding);
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