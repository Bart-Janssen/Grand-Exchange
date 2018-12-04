package DataServer.Database;

import DataServer.SharedServerModels.Item;
import DataServer.SharedServerModels.User;

import java.util.ArrayList;

public interface IDatabaseConnection
{
    User login(User user);
    void register(User user);
    ArrayList<Item> getBackPackItems(int id);
}