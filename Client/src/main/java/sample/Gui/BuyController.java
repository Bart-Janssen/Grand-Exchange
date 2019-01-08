package sample.Gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.Models.MarketOffer;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BuyController extends Controller implements IBuyGui, Initializable
{
    private ArrayList<MarketOffer> offers = new ArrayList<>();
    private MarketOffer selectedOffer;
    public GridPane mainGrid;
    public ScrollPane itemsScrollPane;
    private GridPane offerSpace;
    private GridPane allOffers;
    private Label headerLevel;
    private Label headerPrice;
    private Label headerName;
    private Label headerHealth;
    private Label headerStyle;
    private Rectangle headerImage;

    public BuyController()
    {
        super.getReceiveLogic().setController(this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        fillItemGrid();
    }

    @Override
    public void fillOffers(ArrayList<MarketOffer> offers)
    {
        this.offers = offers;
        if (offers.size() == 0)
        {
            noItemsFound();
            return;
        }
        fillItemGrid();
    }

    @Override
    public void switchToBackPack(String message)
    {
        super.appendChat(message);
        super.openForm(((Stage)mainGrid.getScene().getWindow()), "BackPack");
    }

    private void noItemsFound()
    {
        Platform.runLater(() ->
        {
            allOffers = new GridPane();
            offerSpace = new GridPane();
            Label noItemsFoundLabel = new Label("No items found!");
            noItemsFoundLabel.setStyle("-fx-alignment: center");
            noItemsFoundLabel.setMinWidth(itemsScrollPane.getPrefWidth());
            noItemsFoundLabel.setTextFill(Color.rgb(180, 180, 180));
            offerSpace.add(noItemsFoundLabel, 0, 0);
            allOffers.add(offerSpace,0, 0);
            itemsScrollPane.setContent(allOffers);
        });
    }

    private void fillItemGrid()
    {
        //donker: rgb(39,30,29)
        //licht: rgb(51,40,38)

        fillHeader();
        fillItems();
        Platform.runLater(() -> itemsScrollPane.setContent(allOffers));
    }

    private void fillItems()
    {
        for (int i = 0; i < offers.size(); i++)
        {
            int id = i;

            offerSpace = new GridPane();
            offerSpace.setPrefWidth(itemsScrollPane.getPrefWidth());
            Label col0Space = new Label();
            col0Space.setPrefWidth(50);
            col0Space.setFont(new Font(5));

            Label col3Space = new Label();
            col3Space.setPrefWidth(50);
            col3Space.setFont(new Font(5));

            Rectangle image = new Rectangle();
            image.setHeight(80);
            image.setWidth(80);
            image.setFill(new ImagePattern(new Image(offers.get(id).getItem().getIconPath())));

            GridPane textGrid = new GridPane();
            textGrid.setHgap(5);
            Label level = new Label("Level: " + offers.get(id).getItem().getItemLevel());
            level.setTextFill(Color.rgb(180, 180, 180));
            Label price = new Label("Price: " + super.getCalculateLogic().checkPriceInput(Integer.toString(offers.get(i).getPrice()), offers.get(i).getPrice()));
            price.setTextFill(Color.rgb(180, 180, 180));
            Label name = new Label("Name: " + offers.get(i).getItem().getName());
            name.setTextFill(Color.rgb(180, 180, 180));
            Label health = new Label("Health: " + offers.get(i).getItem().getItemHealth() + "%");
            health.setTextFill(Color.rgb(180, 180, 180));
            Label style = new Label("Style: " + offers.get(i).getItem().getAttackStyle().toString().substring(0,1).toUpperCase() + offers.get(i).getItem().getAttackStyle().toString().substring(1).toLowerCase());
            style.setTextFill(Color.rgb(180, 180, 180));

            textGrid.add(level, 1, 0);
            textGrid.add(price, 1, 1);
            textGrid.add(name, 1, 2);
            textGrid.add(health, 1, 3);
            textGrid.add(style, 1, 4);

            offerSpace.add(col0Space, 0, 0);
            offerSpace.add(image, 1, 1);
            offerSpace.add(textGrid, 2, 1);
            offerSpace.add(col3Space, 0, 2);
            offerSpace.setStyle("-fx-background-color: rgb(51,40,38)");
            offerSpace.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->
            {
                if (e.getButton() == MouseButton.PRIMARY) fillBuyOffer(id);
                e.consume();
            });

            //Final offer space for events
            GridPane finalOfferSpace = offerSpace;
            offerSpace.addEventHandler(MouseEvent.MOUSE_ENTERED, e ->
            {
                finalOfferSpace.setStyle("-fx-background-color: rgb(57,45,40)");
                e.consume();
            });
            offerSpace.addEventHandler(MouseEvent.MOUSE_EXITED, e ->
            {
                finalOfferSpace.setStyle("-fx-background-color: rgb(51,40,38)");
                e.consume();
            });

            allOffers.add(offerSpace,0, i);
        }
    }

    private void fillHeader()
    {
        Platform.runLater(() ->
        {
            Label coll0Space = new Label();
            coll0Space.setPrefWidth(60);
            GridPane searchHeader = new GridPane();
            searchHeader.setStyle("-fx-background-color: rgb(51,40,38)");
            searchHeader.setVgap(5);
            TextField searchTextField = new TextField();
            searchTextField.setStyle("-fx-text-fill: rgb(180, 180, 180); -fx-background-color: rgb(39,30,29); -fx-alignment: center");
            searchTextField.addEventFilter(KeyEvent.KEY_PRESSED, e ->
            {
                if (e.getCode().equals(KeyCode.ENTER)) getSendLogic().getSearchOffers(searchTextField.getText());
            });
            Label coll3Space = new Label();
            coll3Space.setPrefWidth(60);
            searchTextField.setPrefWidth(200);

            headerImage = new Rectangle();
            headerImage.setHeight(80);
            headerImage.setWidth(80);
            headerImage.setFill(Color.rgb(39, 30, 29));

            searchHeader.add(coll0Space, 0, 0);
            GridPane headerTextGrid = new GridPane();
            headerTextGrid.setHgap(5);
            headerLevel = new Label();
            headerLevel.setTextFill(Color.rgb(180, 180, 180));
            headerPrice = new Label();
            headerPrice.setTextFill(Color.rgb(180, 180, 180));
            headerName = new Label();
            headerName.setTextFill(Color.rgb(180, 180, 180));
            headerHealth = new Label();
            headerHealth.setTextFill(Color.rgb(180, 180, 180));
            headerStyle = new Label();
            headerStyle.setTextFill(Color.rgb(180, 180, 180));

            headerTextGrid.add(headerLevel, 1, 0);
            headerTextGrid.add(headerPrice, 1, 1);
            headerTextGrid.add(headerName, 1, 2);
            headerTextGrid.add(headerHealth, 1, 3);
            headerTextGrid.add(headerStyle, 1, 4);

            Button buttonBuy = new Button("Buy");
            buttonBuy.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->
            {
                if (super.getUser().getCoins() >= selectedOffer.getPrice())
                {
                    super.getSendLogic().buyItem(selectedOffer);
                }
                else
                {
                    super.appendChat("You cannot afford this item.");
                }
            });

            searchHeader.add(coll3Space, 0, 0);
            searchHeader.add(headerImage, 1, 2);
            searchHeader.add(headerTextGrid, 2, 2);

            mainGrid.add(searchHeader, 0, 0);
            mainGrid.add(buttonBuy, 0, 1);
            mainGrid.add(searchTextField, 0, 2);

            allOffers = new GridPane();
            allOffers.setVgap(5);
            allOffers.setStyle("-fx-background-color: rgb(39,30,29)");
            mainGrid.setStyle("-fx-background-color: rgb(39,30,29)");
        });
    }

    private void fillBuyOffer(int id)
    {
        Platform.runLater(() ->
        {
            selectedOffer = offers.get(id);
            headerImage.setFill(new ImagePattern(new Image(selectedOffer.getItem().getIconPath())));
            headerLevel.setText("Level: " + selectedOffer.getItem().getItemLevel());
            headerPrice.setText("Price: " + super.getCalculateLogic().checkPriceInput(Integer.toString(selectedOffer.getPrice()), selectedOffer.getPrice()));
            headerName.setText("Name: " + selectedOffer.getItem().getName());
            headerHealth.setText("Health: " + selectedOffer.getItem().getItemHealth() + "%");
            headerStyle.setText("Style: " + selectedOffer.getItem().getAttackStyle().toString().substring(0, 1).toUpperCase() + selectedOffer.getItem().getAttackStyle().toString().substring(1).toLowerCase());
        });
    }

    public void buttonBack_Click(ActionEvent actionEvent)
    {
        super.openForm(((Stage)mainGrid.getScene().getWindow()),"Market");
    }
}

