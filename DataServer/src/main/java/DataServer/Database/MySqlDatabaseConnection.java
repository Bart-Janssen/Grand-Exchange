package DataServer.Database;

import DataServer.SharedServerModels.AttackStyle;
import DataServer.SharedServerModels.Item;
import DataServer.SharedServerModels.User;
import java.sql.*;
import java.util.ArrayList;

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

    public User login(User user)
    {
        Connection con = null;
        String query = "SELECT * FROM user WHERE user.name LIKE ? AND user.password LIKE ?";
        try
        {
            con = DriverManager.getConnection(sqlDatabase, sqlUsername, sqlPassword);
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
            {
                User authenticatedUser = new User(rs.getString("name"), "", rs.getInt("level"), rs.getInt("id"));
                con.close();
                return authenticatedUser;
            }
            con.close();
            return null;

        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            closeConnection(con);
        }
    }

    public void register(User user)
    {

    }

    @Override
    public ArrayList<Item> getBackPackItems(int id)
    {
        Connection con = null;
        ArrayList<Item> items = new ArrayList<>();
        String query =
                "SELECT item.id, item.name, item.obtainDate, item.health, item.level, attackstyle.type FROM backpack " +
                "INNER JOIN user ON backpack.userId = user.id " +
                "INNER JOIN item ON backpack.itemId = item.id " +
                "INNER JOIN attackstyle ON item.attackStyleId = attackstyle.id " +
                "WHERE user.id = ?";
        try
        {
            con = DriverManager.getConnection(sqlDatabase, sqlUsername, sqlPassword);
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                items.add(new Item(rs.getInt("level"), AttackStyle.valueOf(rs.getString("type").toUpperCase()), rs.getString("name"), rs.getInt("health"), rs.getDate("obtainDate")));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeConnection(con);
        }
        return items;
    }

    private void closeConnection(Connection connection)
    {
        try
        {
            connection.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}