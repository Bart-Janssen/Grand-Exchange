package sample.WebSocketConnection;

import com.google.gson.Gson;
import sample.Models.*;

public class WebSocketConnection implements IConnection
{
    @Override
    public void login(User user)
    {
        WebSocketMessage webSocketMessage = new WebSocketMessage();
        webSocketMessage.setUser(user);
        webSocketMessage.setOperation(MessageType.LOGIN);
        UserSession.getInstance().getSession().getAsyncRemote().sendText(new Gson().toJson(webSocketMessage));
    }

    @Override
    public void sellItem(MarketOffer offer)
    {
        WebSocketMessage webSocketMessage = new WebSocketMessage();
        webSocketMessage.addMarketOffer(offer);
        webSocketMessage.setOperation(MessageType.SELL_ITEM);
        UserSession.getInstance().getSession().getAsyncRemote().sendText(new Gson().toJson(webSocketMessage));
    }

    @Override
    public void calculateItemPrice(Item item)
    {
        WebSocketMessage webSocketMessage = new WebSocketMessage();
        webSocketMessage.addItem(item);
        webSocketMessage.setOperation(MessageType.CALCULATE_ITEM_PRICE);
        UserSession.getInstance().getSession().getAsyncRemote().sendText(new Gson().toJson(webSocketMessage));
    }

    @Override
    public void getBackPackItems()
    {
        WebSocketMessage webSocketMessage = new WebSocketMessage();
        webSocketMessage.setOperation(MessageType.GET_BACKPACK_ITEMS);
        UserSession.getInstance().getSession().getAsyncRemote().sendText(new Gson().toJson(webSocketMessage));
    }

    @Override
    public void generateNewWeapon()
    {
        WebSocketMessage webSocketMessage = new WebSocketMessage();
        webSocketMessage.setOperation(MessageType.GENERATE_NEW_WEAPON);
        UserSession.getInstance().getSession().getAsyncRemote().sendText(new Gson().toJson(webSocketMessage));
    }

    @Override
    public void sentHeartBeat()
    {
        System.out.println("Heart beat sent.");
        WebSocketMessage webSocketMessage = new WebSocketMessage();
        webSocketMessage.setOperation(MessageType.HEARTBEAT);
        UserSession.getInstance().getSession().getAsyncRemote().sendText(new Gson().toJson(webSocketMessage));
    }

    @Override
    public void deleteItemFromBackPack(Item item)
    {
        WebSocketMessage webSocketMessage = new WebSocketMessage();
        webSocketMessage.setOperation(MessageType.DELETE_ITEM_FROM_BACKPACK);
        webSocketMessage.addItem(item);
        UserSession.getInstance().getSession().getAsyncRemote().sendText(new Gson().toJson(webSocketMessage));
    }

    @Override
    public void logout()
    {
        WebSocketMessage webSocketMessage = new WebSocketMessage();
        webSocketMessage.setOperation(MessageType.LOGOUT);
        UserSession.getInstance().getSession().getAsyncRemote().sendText(new Gson().toJson(webSocketMessage));
    }

    @Override
    public void getMarketOffers()
    {
        WebSocketMessage webSocketMessage = new WebSocketMessage();
        webSocketMessage.setOperation(MessageType.GET_MARKET_OFFERS);
        UserSession.getInstance().getSession().getAsyncRemote().sendText(new Gson().toJson(webSocketMessage));
    }

    @Override
    public void cancelOffer(MarketOffer offer)
    {
        WebSocketMessage webSocketMessage = new WebSocketMessage();
        webSocketMessage.addMarketOffer(offer);
        webSocketMessage.setOperation(MessageType.CANCEL_OFFER);
        UserSession.getInstance().getSession().getAsyncRemote().sendText(new Gson().toJson(webSocketMessage));
    }

    @Override
    public void getMarketOffersCount()
    {
        WebSocketMessage webSocketMessage = new WebSocketMessage();
        webSocketMessage.setOperation(MessageType.GET_MARKET_OFFERS_COUNT);
        UserSession.getInstance().getSession().getAsyncRemote().sendText(new Gson().toJson(webSocketMessage));
    }
}