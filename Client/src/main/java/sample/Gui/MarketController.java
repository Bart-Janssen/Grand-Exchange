package sample.Gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import sample.Models.Item;
import sample.Models.MarketOffer;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MarketController extends Controller implements IMarketGui, Initializable
{
    public GridPane marketForm;
    public GridPane gridMarket;
    private static ArrayList<MarketOffer> offers = new ArrayList<>();

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
    public void addItemsToMarket(ArrayList<MarketOffer> offers)
    {
        MarketController.offers = offers;
        fillMarket();
    }

    @Override
    public void sellItem(Item item)
    {
        //super.getSendLogic().sellItem(1, item);//TODO: get price out from ui
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
                Label gridCol0 = new Label();
                gridCol0.setPrefWidth(76);

                GridPane gridPane = new GridPane();
                gridPane.setStyle("-fx-background-color:  rgb(51,40,38)");
                gridPane.setVgap(5);
                gridPane.setHgap(5);

                Label level = new Label("Level: " + offers.get(i).getItem().getItemLevel());
                level.setTextFill(Color.rgb(180, 180, 180));
                Label style = new Label("Style: " + offers.get(i).getItem().getAttackStyle().toString().substring(0,1).toUpperCase() + offers.get(i).getItem().getAttackStyle().toString().substring(1).toLowerCase());
                style.setTextFill(Color.rgb(180, 180, 180));
                Label price = new Label("Price: " + offers.get(i).getPrice());
                price.setTextFill(Color.rgb(180, 180, 180));
                Label offer = new Label(offers.get(i).getType().toString().substring(0,1).toUpperCase() + offers.get(i).getType().toString().substring(1).toLowerCase() + " offer");
                offer.setTextFill(Color.rgb(180, 180, 180));
                Label health = new Label("Health: " + offers.get(i).getItem().getItemHealth() + "%");
                health.setTextFill(Color.rgb(180, 180, 180));

                gridPane.add(gridCol0, 0, 0);
                Rectangle image = new Rectangle(100, 100);
                image.setFill(new ImagePattern(new Image(offers.get(i).getItem().getIconPath())));
                gridPane.add(image, 1, 1);
                gridPane.add(level, 1, 2);
                gridPane.add(style, 1, 3);
                gridPane.add(offer, 1, 4);
                gridPane.add(price, 1, 5);
                gridPane.add(health, 1, 6);

                gridMarket.add(gridPane, i,0);
            }
        });
    }
}