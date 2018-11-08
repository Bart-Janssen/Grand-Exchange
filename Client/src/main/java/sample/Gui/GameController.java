package sample.Gui;

import javafx.event.ActionEvent;
import sample.Models.AttackStyle;
import sample.Models.Item;

public class GameController extends Gui implements IGameGui
{
    public void Button_Click(ActionEvent actionEvent)
    {
        super.getSendLogic().sellItem(new Item(50, AttackStyle.MAGIC));
    }

    public GameController()
    {
        super.getReceiveLogic().setGui(this);
    }

    @Override
    public void MBOX(String ding)
    {
        System.out.println(ding);
    }
}