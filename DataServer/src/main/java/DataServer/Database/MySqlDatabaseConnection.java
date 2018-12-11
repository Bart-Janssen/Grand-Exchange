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
                closeConnection(con);
                return authenticatedUser;
            }
            closeConnection(con);
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
                "SELECT item.id, item.name, item.obtainDate, item.health, item.level, attackstyle.type FROM item " +
                "INNER JOIN user ON item.userId = user.id " +
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
                items.add(new Item(rs.getInt("id"), rs.getInt("level"), AttackStyle.valueOf(rs.getString("type").toUpperCase()), rs.getString("name"), rs.getInt("health"), rs.getDate("obtainDate")));
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

    @Override
    public boolean addItemToBackPack(Item item, int userId)
    {
        Connection con = null;
        String query =
                "INSERT INTO item (name, obtainDate, health, level, attackStyleId, userId) " +
                "VALUES (?, ?, ?, ?, (SELECT attackStyle.id FROM attackStyle WHERE attackStyle.type = ?), ?) ";
        try
        {
            con = DriverManager.getConnection(sqlDatabase, sqlUsername, sqlPassword);
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, item.getName());
            stmt.setString(2, item.getObtainDate());
            stmt.setInt(3, item.getItemHealth());
            stmt.setInt(4, item.getItemLevel());
            stmt.setString(5, item.getAttackStyle().toString());
            stmt.setInt(6, userId);
            stmt.executeUpdate();
            closeConnection(con);
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            closeConnection(con);
            return false;
        }
        finally
        {
            closeConnection(con);
        }
    }

    @Override
    public boolean deleteItemFromBackPack(int itemId, int userId)
    {
        Connection con = null;
        String query = "DELETE FROM item WHERE item.id LIKE ? AND item.UserId LIKE ?";
        try
        {
            con = DriverManager.getConnection(sqlDatabase, sqlUsername, sqlPassword);
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, itemId);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
        finally
        {
            closeConnection(con);
        }
        return true;
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