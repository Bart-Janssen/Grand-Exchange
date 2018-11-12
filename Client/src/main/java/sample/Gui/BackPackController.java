package sample.Gui;

import javafx.scene.layout.GridPane;

public class BackPackController extends Gui implements IBackPackGui
{
    public GridPane backPackForm;

    public BackPackController()
    {
        super.getReceiveLogic().setGui(this);
    }
}