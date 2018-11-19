package sample.Gui;

import javafx.scene.layout.GridPane;

public class BackPackController extends Controller implements IBackPackGui
{
    public GridPane backPackForm;

    public BackPackController()
    {
        super.getReceiveLogic().setController(this);
    }
}