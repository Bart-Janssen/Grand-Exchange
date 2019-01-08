package sample.Logic;

import com.google.gson.Gson;
import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.application.Platform;
import sample.Gui.*;
import sample.Models.*;
import javax.websocket.*;

@ClientEndpoint
public class GrandExchangeReceiveLogic implements IGrandExchangeReceiveLogic
{
    private Controller controller;
    private static GrandExchangeReceiveLogic instance = null;

    public static GrandExchangeReceiveLogic getInstance()
    {
        if (instance == null) instance = new GrandExchangeReceiveLogic();
        return instance;
    }

    private GrandExchangeReceiveLogic(){}

    @Override
    public void setController(Controller controller)
    {
        this.controller = controller;
    }

    @OnOpen
    public void onWebSocketConnect()
    {
        System.out.println("[Connected to server]");
    }

    @OnMessage
    public void onWebSocketText(String message)
    {
        handleServerMessage(message);
    }

    @OnClose
    public void onWebSocketClose(CloseReason reason)
    {
        System.out.println("[Closed] : " + reason);
    }

    @OnError
    public void onWebSocketError(Throwable cause)
    {
        System.out.println("[ERROR] : " + cause.getMessage());
    }

    private void handleServerMessage(String jsonMessage)
    {

        final WebSocketMessage webSocketMessage;
        try
        {
            webSocketMessage = new Gson().fromJson(jsonMessage, WebSocketMessage.class);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return;
        }
        MessageType type = webSocketMessage.getOperation();
        switch (type)
        {
            case LOGIN:
                login(webSocketMessage);
                break;
            case SELL_ITEM:
                ((IMarketGui)controller).addItemsToMarket(webSocketMessage.getOffers(), webSocketMessage.getMessage());
                break;
            case BUY_ITEM:
                ((IBuyGui)controller).switchToBackPack(webSocketMessage.getMessage());
                break;
            case SOLD:
                itemSold(webSocketMessage);
                break;
            case CALCULATE_ITEM_PRICE:
                ((IPriceConfirmGui)controller).showCalculatedPrice(webSocketMessage.getOffers().get(0));
                break;
            case GET_BACKPACK_ITEMS:
                ((IBackPackGui)controller).addItemsToBackPack(webSocketMessage.getItems());
                break;
            case GET_MARKET_OFFERS:
                ((IMarketGui)controller).addItemsToMarket(webSocketMessage.getOffers(), webSocketMessage.getMessage());
                break;
            case GENERATE_NEW_WEAPON:
                ((IBackPackGui)controller).addItemsToBackPack(webSocketMessage.getItems());
                break;
            case DELETE_ITEM_FROM_BACKPACK:
                ((IBackPackGui)controller).deletedItem(webSocketMessage.getMessage());
                break;
            case CANCEL_OFFER:
                controller.appendChat(webSocketMessage.getMessage());
                break;
            case GET_MARKET_OFFERS_COUNT:
                ((IBackPackGui)controller).setMarketOfferCount(Integer.parseInt(webSocketMessage.getMessage()));
                break;
            case GET_SEARCH_OFFERS:
                ((IBuyGui)controller).fillOffers(webSocketMessage.getOffers());
                break;
            case NEW_OFFER_PLACED_ON_MARKET:
                addMessageToChat(webSocketMessage);
                break;
        }
    }

    private void addMessageToChat(WebSocketMessage webSocketMessage)
    {
        controller.appendChat(webSocketMessage.getMessage());
        if (controller instanceof IGameGui)  ((IGameGui)controller).addMessages();
    }

    private void itemSold(WebSocketMessage webSocketMessage)
    {
        addMessageToChat(webSocketMessage);
        if (controller instanceof IMarketGui) ((IMarketGui)controller).setItemToSoldStatus(webSocketMessage.getItems().get(0).getId());
        MarketController.addSoldItem(webSocketMessage.getItems().get(0).getId());
    }

    private void login(WebSocketMessage webSocketMessage)
    {
        if (webSocketMessage.getUser() == null)
        {
            ((ILoginGui)controller).loginFailed();
            System.out.println("failed login");
            return;
        }
        if (webSocketMessage.getUser().isLoggedIn())
        {
            controller.appendChat(webSocketMessage.getMessage());
            controller.setUser(webSocketMessage.getUser());
            ((ILoginGui)controller).callGameGui();
        }
    }
}