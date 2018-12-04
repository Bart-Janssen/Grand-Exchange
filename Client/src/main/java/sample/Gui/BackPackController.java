package sample.Gui;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import sample.Models.Item;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BackPackController extends Controller implements IBackPackGui, Initializable
{
    public GridPane backPackForm;
    public GridPane gridPaneBackPack;
    private static ArrayList<Item> backPack = new ArrayList<>();
    private final int maxMarketOffers = 3;

    public BackPackController()
    {
        super.getReceiveLogic().setController(this);
    }

    public void buttonBack_Click(ActionEvent actionEvent)
    {
        super.openForm(((Stage)backPackForm.getScene().getWindow()),"Game", "Game", 300, 300);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        super.getSendLogic().getBackPackItems();
    }

    @Override
    public void switchToMarketController()
    {
        super.openForm(((Stage)backPackForm.getScene().getWindow()),"Market", "Market", 800, 300);
    }

    @Override
    public void addItemsToBackPack(ArrayList<Item> items)
    {
        backPack = items;
        Platform.runLater(() ->
        {
            for (int x = 0; x < backPack.size(); x++)
            {
                ContextMenu rightClickMenu = new ContextMenu();
                rightClickMenu.addEventFilter(MouseEvent.MOUSE_RELEASED, event ->
                {
                    if (event.getButton() == MouseButton.SECONDARY) event.consume();
                });
                rightClickMenu.getItems().add(new MenuItem("Sell"));
                int id = x;
                rightClickMenu.setOnAction(event ->
                {
                    if (MarketController.getOfferCount() < maxMarketOffers)
                    {
                        getSendLogic().calculateItemPrice(backPack.get(id));
                        backPack.remove(id);
                        return;
                    }
                    System.out.println("Cannot put more offers in the market.");//TODO: melding
                });
                Rectangle rectangle = new Rectangle(gridPaneBackPack.getPrefWidth() / gridPaneBackPack.getColumnConstraints().size(), gridPaneBackPack.getPrefHeight() / gridPaneBackPack.getRowConstraints().size());
                rectangle.addEventHandler(MouseEvent.MOUSE_RELEASED, e ->
                {
                    if (e.getButton() == MouseButton.SECONDARY) rightClickMenu.show(rectangle, e.getScreenX(), e.getScreenY());
                    e.consume();
                });
                rectangle.setFill(Color.rgb(21,77,128));
                rectangle.setFill(new ImagePattern(new Image(backPack.get(x).getIconPath())));//TODO: name
                gridPaneBackPack.add(rectangle, x % 4, (int)Math.floor((double)x / 4), 1, 1);
            }
        });
    }
}