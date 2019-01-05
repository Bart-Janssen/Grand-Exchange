package sample.Gui;

import sample.Models.MarketOffer;
import java.util.ArrayList;

public interface IMarketGui
{
    void setItemToSoldStatus(int id);
    void addItemsToMarket(ArrayList<MarketOffer> offers, String message);
}