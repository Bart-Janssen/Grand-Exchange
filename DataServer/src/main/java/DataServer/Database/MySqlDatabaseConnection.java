package DataServer.Database;

import DataServer.SharedServerModels.User;
import java.sql.*;

public class MySqlDatabaseConnection implements IDatabaseConnection
{
    private String sqlDatabase;
    private String sqlUsername;
    private String sqlPassword;

    public MySqlDatabaseConnection(String sqlDatabase, String sqlUsername, String sqlPassword)
    {
        this.sqlDatabase = sqlDatabase;
        this.sqlUsername = sqlUsername;
        this.sqlPassword = sqlPassword;
    }

    public boolean login(User user)
    {
        String query = "SELECT * FROM user WHERE user.name LIKE ? AND user.password LIKE ?";
        try
        {
            Connection con = DriverManager.getConnection(sqlDatabase, sqlUsername, sqlPassword);
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
            {
                con.close();
                return true;
            }
            con.close();
            return false;

        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public void register(User user)
    {

    }
}