package sample.Gui;

import sample.Models.Item;

import java.util.ArrayList;

public interface IBackPackGui
{
    void switchToMarketController();
    void addItemsToBackPack(ArrayList<Item> items);
}