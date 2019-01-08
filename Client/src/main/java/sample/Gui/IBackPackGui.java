package sample.Gui;

import sample.Models.Item;

import java.util.ArrayList;

public interface IBackPackGui
{
    void addItemsToBackPack(ArrayList<Item> items);
    void deletedItem(String message);
    void setMarketOfferCount(int count);
}