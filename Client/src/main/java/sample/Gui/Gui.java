package sample.Gui;

import sample.Factory.ClientFactory;
import sample.Logic.IGrandExchangeReceiveLogic;
import sample.Logic.IGrandExchangeSendLogic;
import sample.Models.WebSocketType;

public abstract class Gui
{
    private IGrandExchangeSendLogic sendLogic = ClientFactory.getInstance().makeNewGrandExchangeSendLogic(WebSocketType.WEBSOCKETSERVER);
    private IGrandExchangeReceiveLogic receiveLogic = ClientFactory.getInstance().makeNewGrandExchangeReceiveLogic(WebSocketType.WEBSOCKETSERVER);

    public IGrandExchangeSendLogic getSendLogic()
    {
        return sendLogic;
    }

    public IGrandExchangeReceiveLogic getReceiveLogic()
    {
        return receiveLogic;
    }
}