package DataServer.DataServerLogic;

import DataServer.SharedServerModels.Item;
import DataServer.SharedServerModels.User;

import java.util.ArrayList;

public interface IDataServerLogic
{
    User login(User user);
    void register(User user);
    ArrayList<Item> getBackPackItems(int id);
    boolean addItemToBackPack(Item item, int userId);
    boolean deleteItemFromBackPack(int itemId, int userId);
}