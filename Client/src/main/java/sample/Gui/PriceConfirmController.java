package sample.Gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.Models.Item;
import sample.Models.MarketOffer;

import java.net.URL;
import java.util.ResourceBundle;

public class PriceConfirmController extends Controller implements IPriceConfirmGui, Initializable
{
    private static MarketOffer offer;
    private static Item temperaryItem;

    public GridPane priceConfirmForm;
    public GridPane gridPanePrice;
    private GridPane gridPaneMarketOffer;
    private GridPane textPane;
    private Label name;
    private Label offerType;
    private Label level;
    private Label style;
    private Label health;
    private Label price;
    private Label gridCol0;
    private Button buttonSell;
    private Rectangle image;
    private TextField newPrice;

    public PriceConfirmController()
    {
        super.getReceiveLogic().setController(this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        super.getSendLogic().calculateItemPrice(temperaryItem);
        temperaryItem = null;
    }

    public static void setItem(Item item)
    {
        temperaryItem = item;
    }

    public void buttonBack_Click(ActionEvent actionEvent)
    {
        super.openForm(((Stage)priceConfirmForm.getScene().getWindow()),"BackPack", "BackPack", 300, 300);
    }

    @Override
    public void showCalculatedPrice(MarketOffer offer)
    {
        PriceConfirmController.offer = offer;
        fillPriceConfirm();
    }

    private void fillPriceConfirm()
    {
        Platform.runLater(() ->
        {
            //Invisible placeholder
            gridCol0 = new Label();
            gridCol0.setPrefWidth(50);
            gridCol0.setFont(new Font(1));

            //GridPane Item
            gridPaneMarketOffer = new GridPane();
            gridPaneMarketOffer.setStyle("-fx-background-color: rgb(51,40,38)");
            gridPaneMarketOffer.setVgap(5);
            gridPaneMarketOffer.setHgap(5);

            //Labels
            name = new Label("Name: " + offer.getItem().getName());
            name.setTextFill(Color.rgb(180, 180, 180));
            offerType = new Label(offer.getType().toString().substring(0, 1).toUpperCase() + offer.getType().toString().substring(1).toLowerCase() + " offer");
            offerType.setTextFill(Color.rgb(180, 180, 180));
            level = new Label("Level: " + offer.getItem().getItemLevel());
            level.setTextFill(Color.rgb(180, 180, 180));
            style = new Label("Style: " + offer.getItem().getAttackStyle().toString().substring(0, 1).toUpperCase() + offer.getItem().getAttackStyle().toString().substring(1).toLowerCase());
            style.setTextFill(Color.rgb(180, 180, 180));
            health = new Label("Health: " + offer.getItem().getItemHealth() + "%");
            health.setTextFill(Color.rgb(180, 180, 180));

            //Image
            gridPaneMarketOffer.add(gridCol0, 0, 0);
            image = new Rectangle(100, 100);
            image.setFill(new ImagePattern(new Image(offer.getItem().getIconPath())));
            gridPaneMarketOffer.add(image, 1, 1);

            //Text pane
            textPane = new GridPane();
            textPane.setStyle("-fx-background-color: rgb(51,40,38)");
            textPane.setVgap(5);
            textPane.setHgap(5);
            gridPaneMarketOffer.add(textPane, 2, 1);

            //Grid settings
            GridPane.setValignment(textPane, VPos.TOP);
            GridPane.setValignment(image, VPos.TOP);
            GridPane.setHalignment(offerType, HPos.CENTER);
            GridPane.setValignment(offerType, VPos.TOP);

            //Add text to text pane
            textPane.add(name, 0, 0);
            textPane.add(level, 0, 1);
            textPane.add(style, 0, 2);
            textPane.add(health, 0, 3);

            //Price Grid pane
            gridPanePrice.setVgap(5);
            gridPanePrice.setHgap(5);

            buttonSell = new Button("Sell");
            buttonSell.addEventFilter(MouseEvent.MOUSE_CLICKED, e->
            {
                super.getSendLogic().sellItem(offer);
                offer = null;
                super.openForm(((Stage)priceConfirmForm.getScene().getWindow()), "Market", "Market", 800, 300);
            });

            price = new Label("Price:");
            price.setPrefWidth(50);
            price.setStyle("-fx-text-fill: rgb(180, 180, 180); -fx-alignment: center;");

            newPrice = new TextField(super.getCalculateLogic().checkPriceInput(Integer.toString(offer.getPrice()), offer.getPrice()));
            newPrice.setMaxWidth(100);
            newPrice.setStyle("-fx-padding: 0px; -fx-focus-color: transparent; -fx-text-fill: rgb(180, 180, 180); -fx-background-color: rgb(51,40,38); -fx-alignment: center;");
            newPrice.addEventFilter(KeyEvent.KEY_PRESSED, e->
            {
                if (e.getCode().equals(KeyCode.ENTER)) buttonSell.requestFocus();
            });
            newPrice.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) ->
            {
                if (!newPropertyValue)
                {
                    newPrice.setText(super.getCalculateLogic().checkPriceInput(newPrice.getText(), offer.getPrice()));
                    offer.setPrice(Integer.parseInt(newPrice.getText().replace(".", "")));
                }
            });

            //Grid settings
            GridPane.setHalignment(price, HPos.CENTER);
            GridPane.setValignment(price, VPos.BOTTOM);
            GridPane.setHalignment(newPrice, HPos.CENTER);
            GridPane.setValignment(newPrice, VPos.TOP);
            GridPane.setHalignment(buttonSell, HPos.CENTER);

            gridPanePrice.add(newPrice, 0, 0);
            gridPanePrice.add(buttonSell, 0, 1);

            priceConfirmForm.add(offerType, 0, 1);
            priceConfirmForm.add(gridPaneMarketOffer, 0, 2);
            priceConfirmForm.add(price, 0, 3);
        });
    }
}