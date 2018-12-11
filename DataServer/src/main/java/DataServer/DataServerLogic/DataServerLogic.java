package DataServer.DataServerLogic;

import DataServer.Database.IDatabaseConnection;
import DataServer.SharedServerModels.Item;
import DataServer.SharedServerModels.User;

import java.util.ArrayList;

public class DataServerLogic implements IDataServerLogic
{
    private IDatabaseConnection database;

    public DataServerLogic(IDatabaseConnection database)
    {
        this.database = database;
    }

    public User login(User user)
    {
        return database.login(user);
    }

    public void register(User user)
    {

    }

    @Override
    public ArrayList<Item> getBackPackItems(int id)
    {
        return database.getBackPackItems(id);
    }

    @Override
    public boolean addItemToBackPack(Item item, int userId)
    {
        return database.addItemToBackPack(item, userId);
    }

    @Override
    public boolean deleteItemFromBackPack(int itemId, int userId)
    {
        return database.deleteItemFromBackPack(itemId, userId);
    }
}