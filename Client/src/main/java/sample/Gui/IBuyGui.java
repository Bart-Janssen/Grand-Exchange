package sample.Gui;

import sample.Models.MarketOffer;
import java.util.ArrayList;

public interface IBuyGui
{
    void fillOffers(ArrayList<MarketOffer> offers);
    void switchToBackPack(String message);
}