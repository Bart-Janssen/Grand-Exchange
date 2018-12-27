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
                ((IBackPackGui)controller).canceledOrder(webSocketMessage.getMessage());
                break;
            case GET_MARKET_OFFERS_COUNT:
                ((IBackPackGui)controller).setMarketOfferCount(Integer.parseInt(webSocketMessage.getMessage()));
                break;
        }
    }

    private void login(WebSocketMessage webSocketMessage)
    {
        if (webSocketMessage.getUser().isLoggedIn())
        {
            ((ILoginGui)controller).callGameGui();
            return;
        }
        ((ILoginGui)controller).loginFailed();
    }
}