package sample.Logic;

import sample.Models.Item;

public interface IGrandExchangeSendLogic
{
    void login(String username, String password);
    void sellItem(Item item);
}