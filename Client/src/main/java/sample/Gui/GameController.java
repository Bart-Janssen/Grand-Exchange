package sample.Gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameController extends Controller implements IGameGui, Initializable
{
    public GridPane mainGrid;
    private static ArrayList<String> messages = new ArrayList<>();
    public ListView<String> listViewMessages;
    public Label labelName;
    public Label labelLevel;
    public Label labelCoins;

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
        messages.clear();
        super.clearMessages();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        setUserDetails();
        addMessages();
    }

    @Override
    public void addMessages()
    {
        listViewMessages.setStyle("-fx-font: 8pt \"Arial\"; -fx-padding: 0px; -fx-border: none");
        messages = super.getMessages();
        Platform.runLater(() -> listViewMessages.getItems().setAll(messages));
        if (messages.size() > 7) listViewMessages.scrollTo(messages.get(messages.size() - 1));
    }

    private void setUserDetails()
    {
        Platform.runLater(() ->
        {
            labelName.setText("Name: " + super.getUser().getUsername());
            labelLevel.setText("Level: " + super.getUser().getLevel());
            labelCoins.setText("Coins: " + super.getCalculateLogic().checkPriceInput(Integer.toString(super.getUser().getCoins()), super.getUser().getCoins()));
        });
    }
}