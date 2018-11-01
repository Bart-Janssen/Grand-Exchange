package DataServer.Factory;

import DataServer.DataServerLogic.DataServerLogic;
import DataServer.DataServerLogic.IDataServerLogic;
import DataServer.Database.HashMapDatabase;
import DataServer.Database.MySqlDatabaseConnection;
import DataServer.Models.DatabaseType;

public class DatabaseServerFactory
{
    private static DatabaseServerFactory instance = null;

    private DatabaseServerFactory(){}

    public static DatabaseServerFactory getInstance()
    {
        if (instance == null) instance = new DatabaseServerFactory();
        return instance;
    }

    public IDataServerLogic makeNewServerLogic(DatabaseType type)
    {
        switch (type)
        {
            case MYSQL:
                return new DataServerLogic(new MySqlDatabaseConnection());
            case HashMapDatabase:
                return new DataServerLogic(new HashMapDatabase());
            default:
                return null;
        }
    }
}