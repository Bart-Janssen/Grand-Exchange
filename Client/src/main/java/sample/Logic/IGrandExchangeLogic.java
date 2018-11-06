package sample.Logic;

import sample.Models.Item;

public interface IGrandExchangeLogic
{
    void login(String username, String password);
    int sellItem(Item item);
}