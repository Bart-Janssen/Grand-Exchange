package sample.Gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    public GridPane marketForm;
    public GridPane gridMarket;
    private GridPane gridPane;
    private Label gridCol0;
    private Label level;
    private Label style;
    private Label price;
    private Label offer;
    private Label health;
    private Rectangle image;
    private Button cancelButton;

    static int getOfferCount()
    {
        return offers.size();
    }

    public MarketController()
    {
        super.getReceiveLogic().setController(this);
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

    public void buttonBack_Click(ActionEvent actionEvent)
    {
        super.openForm(((Stage)marketForm.getScene().getWindow()),"Game", "Game", 300, 300);
    }

    private void fillMarket()
    {
        Platform.runLater(() ->
        {
            for (int i = 0; i < offers.size(); i++)
            {
                int id = i;
                gridCol0 = new Label();
                gridCol0.setPrefWidth(76);

                gridPane = new GridPane();
                gridPane.setStyle("-fx-background-color:  rgb(51,40,38)");
                gridPane.setVgap(5);
                gridPane.setHgap(5);

                cancelButton = new Button("Cancel");
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
                    offers.remove(id);
                    super.openForm(((Stage)marketForm.getScene().getWindow()),"Backpack", "BackPack", 300 , 300);
                });

                gridPane.add(image, 1, 1);
                gridPane.add(level, 1, 2);
                gridPane.add(style, 1, 3);
                gridPane.add(offer, 1, 4);
                gridPane.add(price, 1, 5);
                gridPane.add(health, 1, 6);
                gridPane.add(cancelButton, 2, 7);

                gridMarket.add(gridPane, i,0);
            }
        });
    }
}