package sample.Gui;

import sample.Models.Item;

public class MarketController extends Controller implements IMarketGui
{
    public MarketController()
    {
        super.getReceiveLogic().setController(this);
    }

    @Override
    public void sellItem(Item item)
    {
        super.getSendLogic().sellItem(1, null, item);
    }
}