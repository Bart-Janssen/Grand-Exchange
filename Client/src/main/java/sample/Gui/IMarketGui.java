package sample.Gui;

import sample.Models.Item;

public interface IMarketGui
{
    void sellItem(Item item);
    void showCalculatedPrice(String jsonMessage);
}