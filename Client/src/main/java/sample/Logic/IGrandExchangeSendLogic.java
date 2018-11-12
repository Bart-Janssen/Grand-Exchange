package sample.Logic;

import sample.Models.Item;
import sample.Models.User;

public interface IGrandExchangeSendLogic
{
    void login(String username, String password);
    void sellItem(int price, User user, Item item);
}