package sample.Gui;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController extends Controller implements IGameGui, Initializable
{
    public GridPane mainGrid;

    public GameController()
    {
        super.getReceiveLogic().setController(this);
    }

    public void buttonOpenBackpack_Click(ActionEvent actionEvent)
    {
        super.openForm(((Stage)mainGrid.getScene().getWindow()),"Backpack");
    }

    public void buttonOpenMarket_Click(ActionEvent actionEvent)
    {
        super.openForm(((Stage)mainGrid.getScene().getWindow()),"Market");
    }

    public void buttonGenerateRandomWeapon_Click(ActionEvent actionEvent)
    {
        super.openForm(((Stage)mainGrid.getScene().getWindow()),"Backpack");
        super.getSendLogic().generateNewWeapon();
    }

    public void buttonLogout_Click(ActionEvent actionEvent)
    {
        super.openForm(((Stage)mainGrid.getScene().getWindow()), "Login");
        super.getSendLogic().logout();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        System.out.println("intit");
    }
}