package sample.Gui;

import sample.Models.Item;
import sample.Models.MarketOffer;

import java.util.ArrayList;

public interface IMarketGui
{
    void sellItem(Item item);
    void addItemsToMarket(ArrayList<MarketOffer> offers);
}