package DataServer.DataServerLogic;

import DataServer.Database.IDatabaseConnection;
import DataServer.SharedServerModels.User;

public class DataServerLogic implements IDataServerLogic
{
    private IDatabaseConnection database;

    public DataServerLogic(IDatabaseConnection database)
    {
        this.database = database;
    }

    public boolean login(User user)
    {
        return database.login(user);
    }

    public void register(User user)
    {

    }
}