package sample.Gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.PopupWindow;
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
    private static final int MAX_MARKET_OFFERS = 3;
    private static int marketOfferCount;

    public BackPackController()
    {
        super.getReceiveLogic().setController(this);
        super.getSendLogic().getMarketOffersCount();
    }

    public void buttonBack_Click()
    {
        super.openForm(((Stage)backPackForm.getScene().getWindow()),"Game");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        super.getSendLogic().getBackPackItems();
    }

    @Override
    public void addItemsToBackPack(ArrayList<Item> items)
    {
        backPack = items;
        addGrid();
    }

    @Override
    public void deletedItem(String message)
    {
        super.appendChat(message);
    }

    @Override
    public void setMarketOfferCount(int count)
    {
        marketOfferCount = count;
    }

    private void addGrid()
    {
        Platform.runLater(() ->
        {
            for (int i = 0; i < backPack.size(); i++)
            {
                ContextMenu rightClickMenu = new ContextMenu();
                rightClickMenu.addEventFilter(MouseEvent.MOUSE_RELEASED, event ->
                {
                    if (event.getButton() == MouseButton.SECONDARY) event.consume();
                });
                rightClickMenu.getItems().add(new MenuItem("Sell"));
                rightClickMenu.getItems().add(new MenuItem("Destroy"));
                int id = i;
                rightClickMenu.setOnAction(event ->
                {
                    switch (((MenuItem)event.getTarget()).getText())
                    {
                        case "Sell":
                            sell(id);
                            break;
                        case "Destroy":
                            destroy(id);
                            break;
                            default:
                                break;
                    }
                });
                gridPaneBackPack.add(createRectangleItem(rightClickMenu, id), i % 4, (int) Math.floor((double) i / 4), 1, 1);
            }
        });
    }

    private Rectangle createRectangleItem(ContextMenu rightClickMenu, int id)
    {
        Rectangle rectangle = new Rectangle(gridPaneBackPack.getPrefWidth() / gridPaneBackPack.getColumnConstraints().size(), gridPaneBackPack.getPrefHeight() / gridPaneBackPack.getRowConstraints().size());
        rectangle.addEventHandler(MouseEvent.MOUSE_RELEASED, e ->
        {
            if (e.getButton() == MouseButton.SECONDARY) rightClickMenu.show(rectangle, e.getScreenX(), e.getScreenY());
            e.consume();
        });
        rectangle.addEventHandler(MouseEvent.MOUSE_ENTERED, e ->
        {
            Tooltip tooltip = new Tooltip("level: " + backPack.get(id).getItemLevel() + "\nAttackstyle: " + backPack.get(id).getAttackStyle().toString().toLowerCase() + "\nHealth: " + backPack.get(id).getItemHealth() + "%");
            tooltip.setConsumeAutoHidingEvents(false);
            tooltip.setAnchorLocation(PopupWindow.AnchorLocation.WINDOW_BOTTOM_LEFT);
            Tooltip.install(rectangle, tooltip);
        });
        rectangle.setFill(new ImagePattern(new Image(backPack.get(id).getIconPath())));
        return rectangle;
    }

    private void sell(int id)
    {
        if (marketOfferCount < MAX_MARKET_OFFERS)
        {
            PriceConfirmController.setItem(backPack.get(id));
            super.openForm(((Stage)backPackForm.getScene().getWindow()), "PriceConfirm");
            backPack.remove(id);
            return;
        }
        super.appendChat("Cannot put more offers in the market.");
    }

    private void destroy(int id)
    {
        super.getSendLogic().deleteItemFromBackPack(backPack.get(id));
        backPack.remove(id);
        gridPaneBackPack.getChildren().clear();
        addGrid();
    }
}