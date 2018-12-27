package sample.Gui;

import sample.Models.MarketOffer;
import java.util.ArrayList;

public interface IMarketGui
{
    void addItemsToMarket(ArrayList<MarketOffer> offers, String messsage);
}