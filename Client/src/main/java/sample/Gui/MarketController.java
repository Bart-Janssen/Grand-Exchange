package sample.Gui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import sample.Models.MarketOffer;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MarketController extends Controller implements IMarketGui, Initializable
{
    private static ArrayList<MarketOffer> offers = new ArrayList<>();
    private static ArrayList<Integer> soldItemIds = new ArrayList<>();
    private static final int MAX_OFFER_SPACE = 3;

    public GridPane marketForm;
    public GridPane gridMarket;
    private GridPane gridPane;
    private Label gridCol0;
    private Label level;
    private Label style;
    private Label price;
    private Label offer;
    private Label health;
    private Label soldStatusBar;
    private Rectangle image;
    private Button cancelButton;
    private Button buyButton;

    private static final String soldStatusBarGreen = "-fx-background-color: green";
    private static final String soldStatusBarGray = "-fx-background-color: gray";
    private static final String cancel = "Cancel";
    private static final String remove = "Remove";

    public MarketController()
    {
        super.getReceiveLogic().setController(this);
    }

    public static void addSoldItem(int id)
    {
        soldItemIds.add(id);
    }


    @Override
    public void setItemToSoldStatus(int id)
    {
        addSoldItem(id);
        changeSoldStatus();
        soldItemIds.remove(id);
    }

    private void changeSoldStatus()
    {
        Platform.runLater(() ->
        {
            ArrayList<Integer> indexes = new ArrayList<>();
            for (int soldItemId : soldItemIds)
            {
                for (int i = 0; i < offers.size(); i++)
                {
                    if (soldItemId == offers.get(i).getItem().getId()) indexes.add(i);
                }
            }
            for (int index : indexes)
            {
                for (Node node : ((GridPane) gridMarket.getChildren().get(index)).getChildren())
                {
                    if (node instanceof Label)
                    {
                        if (node.getStyle().equals(soldStatusBarGray)) node.setStyle(soldStatusBarGreen);
                    }
                    if (node instanceof Button)
                    {
                        if (((Button) node).getText().equals(cancel)) ((Button) node).setText(remove);
                    }
                }
            }
        });
    }

    private boolean checkIfItemsSold(int index)
    {
        for (int soldItemId : soldItemIds)
        {
            if (offers.get(index).getItem().getId() == soldItemId) return true;
        }
        return false;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        super.getSendLogic().getMarketOffers();
    }

    @Override
    public void addItemsToMarket(ArrayList<MarketOffer> offers, String message)
    {
        super.appendChat(message);
        MarketController.offers = offers;
        fillMarket();
    }

    public void buttonBack_Click()
    {
        super.openForm(((Stage)marketForm.getScene().getWindow()),"Game");
    }

    private void fillMarket()
    {
        Platform.runLater(() ->
        {
            gridMarket.getChildren().clear();
            for (int i = 0; i < offers.size(); i++)
            {
                boolean itemIsSold = checkIfItemsSold(i);
                int id = i;
                gridCol0 = new Label();
                gridCol0.setPrefWidth(76);

                gridPane = new GridPane();
                gridPane.setStyle("-fx-background-color: rgb(51,40,38)");
                gridPane.setVgap(5);
                gridPane.setHgap(5);

                cancelButton = new Button(itemIsSold ? remove : cancel);
                cancelButton.setStyle("-fx-background-color: gray");
                cancelButton.setEffect(new InnerShadow());
                level = new Label("Level: " + offers.get(i).getItem().getItemLevel());
                level.setTextFill(Color.rgb(180, 180, 180));
                style = new Label("Style: " + offers.get(i).getItem().getAttackStyle().toString().substring(0,1).toUpperCase() + offers.get(i).getItem().getAttackStyle().toString().substring(1).toLowerCase());
                style.setTextFill(Color.rgb(180, 180, 180));
                price = new Label("Price: " + super.getCalculateLogic().checkPriceInput(Integer.toString(offers.get(i).getPrice()), offers.get(i).getPrice()));
                price.setTextFill(Color.rgb(180, 180, 180));
                offer = new Label(offers.get(i).getType().toString().substring(0,1).toUpperCase() + offers.get(i).getType().toString().substring(1).toLowerCase() + " offer");
                offer.setTextFill(Color.rgb(180, 180, 180));
                health = new Label("Health: " + offers.get(i).getItem().getItemHealth() + "%");
                health.setTextFill(Color.rgb(180, 180, 180));

                gridPane.add(gridCol0, 0, 0);
                image = new Rectangle(100, 100);
                image.setFill(new ImagePattern(new Image(offers.get(i).getItem().getIconPath())));

                cancelButton.addEventFilter(MouseEvent.MOUSE_CLICKED, e->
                {
                    super.getSendLogic().cancelOffer(offers.get(id));
                    for (int index = 0; index < FXCollections.observableArrayList(soldItemIds).size(); index++)
                    {
                        if (soldItemIds.get(index) == offers.get(id).getItem().getId())
                        {
                            soldItemIds.remove(index);
                            break;
                        }
                    }
                    offers.remove(id);
                    fillMarket();
                });

                soldStatusBar = new Label();
                soldStatusBar.setPrefWidth(100);
                soldStatusBar.setStyle(itemIsSold ? soldStatusBarGreen : soldStatusBarGray);

                gridPane.add(image, 1, 1);
                gridPane.add(level, 1, 2);
                gridPane.add(style, 1, 3);
                gridPane.add(offer, 1, 4);
                gridPane.add(price, 1, 5);
                gridPane.add(health, 1, 6);
                gridPane.add(soldStatusBar,1,7);
                gridPane.add(cancelButton, 2, 7);

                gridMarket.add(gridPane, i,0);
            }

            for (int i = offers.size(); i < MAX_OFFER_SPACE; i++)
            {
                gridPane = new GridPane();
                gridPane.setStyle("-fx-background-color: rgb(51,40,38)");
                gridPane.setVgap(5);
                gridPane.setHgap(5);

                gridCol0 = new Label();
                gridCol0.setPrefWidth(100);

                buyButton = new Button("Buy");
                buyButton.setEffect(new InnerShadow());
                buyButton.setStyle("-fx-background-color: gray;");
                buyButton.addEventFilter(MouseEvent.MOUSE_CLICKED, e-> super.openForm(((Stage)marketForm.getScene().getWindow()),"Buy"));

                gridPane.add(gridCol0, 0, 0);
                gridPane.add(buyButton, 2, 7);

                gridMarket.add(gridPane, i,0);
            }
        });
    }
}