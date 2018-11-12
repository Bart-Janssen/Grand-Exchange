package sample.Gui;

import sample.Models.Item;

public class MarketController extends Gui implements IMarketGui
{
    public MarketController()
    {
        super.getReceiveLogic().setGui(this);
    }

    @Override
    public void sellItem(Item item)
    {
        super.getSendLogic().sellItem(item);
    }
}