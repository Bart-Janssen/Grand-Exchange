package DataServer.Database;

import DataServer.SharedServerModels.*;
import com.google.gson.Gson;
import java.sql.*;
import java.util.ArrayList;

public class MySqlDatabaseConnection implements IDatabaseConnection
{
    private Connection con = null;
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
                User authenticatedUser = new User(rs.getString("name"), "", rs.getInt("level"), rs.getInt("id"), rs.getInt("coins"));
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
    public ArrayList<Item> getBackPackItems(int userId)
    {
        ArrayList<Item> items = new ArrayList<>();
        String query =
                "SELECT item.id, item.name, item.obtainDate, item.health, item.level, attackstyle.type FROM item " +
                "INNER JOIN user ON item.userId = user.id " +
                "INNER JOIN attackstyle ON item.attackStyleId = attackstyle.id " +
                "WHERE user.id LIKE ? AND item.onMarket LIKE 0 ";
        try
        {
            con = DriverManager.getConnection(sqlDatabase, sqlUsername, sqlPassword);
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                items.add(new Item(rs.getInt("id"), rs.getInt("level"), AttackStyle.valueOf(rs.getString("type").toUpperCase()), rs.getString("name"), rs.getInt("health"), rs.getString("obtainDate")));
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
        String query =
                "INSERT INTO item (name, obtainDate, health, level, attackStyleId, userId, onMarket) " +
                "VALUES (?, ?, ?, ?, (SELECT attackStyle.id FROM attackStyle WHERE attackStyle.type = ?), ?, 0) ";
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

    @Override
    public ArrayList<MarketOffer> getMarketOffers(int userId)
    {
        ArrayList<MarketOffer> offers = new ArrayList<>();
        String query =
                "SELECT marketoffer.id AS id, marketoffer.userId AS userId, marketoffer.price AS price, offertype.type AS type, item.id AS itemId, item.level AS level, attackStyle.Type AS attackStyleType, item.name AS name, item.health AS health, item.obtainDate AS obtainDate FROM marketoffer " +
                "INNER JOIN offertype ON marketoffer.offerTypeId = offertype.id " +
                "INNER JOIN item ON marketoffer.itemId = item.id " +
                "INNER JOIN attackStyle ON item.attackStyleId = attackStyle.id " +
                "WHERE marketoffer.userId LIKE ?";
        try
        {
            con = DriverManager.getConnection(sqlDatabase, sqlUsername, sqlPassword);
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                offers.add(new MarketOffer(rs.getInt("id"), rs.getInt("userId"), rs.getInt("price"),
                                new Item(rs.getInt("itemId"), rs.getInt("level"), AttackStyle.valueOf(rs.getString("attackStyleType").toUpperCase()), rs.getString("name"), rs.getInt("health"), rs.getString("obtainDate")),
                        MarketOfferType.valueOf(rs.getString("type").toUpperCase())));
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
        return offers;
    }

    @Override
    public boolean sellItem(MarketOffer offer)
    {
        String query =
                "INSERT INTO marketoffer (price, userId, itemId, offerTypeId) " +
                "VALUES (?, ?, ?, (SELECT offertype.id FROM offertype WHERE offertype.type LIKE ?))";
        try
        {
            con = DriverManager.getConnection(sqlDatabase, sqlUsername, sqlPassword);
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, offer.getPrice());
            stmt.setInt(2, offer.getUserId());
            stmt.setInt(3, offer.getItem().getId());
            stmt.setString(4, offer.getType().toString());
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
    public boolean cancelOffer(int offerId)
    {
        String query = "DELETE FROM marketoffer WHERE marketoffer.id LIKE ?";
        try
        {
            con = DriverManager.getConnection(sqlDatabase, sqlUsername, sqlPassword);
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, offerId);
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
    }

    @Override
    public ArrayList<MarketOffer> getSearchOffers(String searchQuery, int userId)
    {
        ArrayList<MarketOffer> offers = new ArrayList<>();
        String query =
                "SELECT marketoffer.id AS id, marketoffer.userId AS userId, marketoffer.price AS price, offertype.type AS type, item.id AS itemId, item.level AS level, attackStyle.Type AS attackStyleType, item.name AS name, item.health AS health, item.obtainDate AS obtainDate FROM marketoffer " +
                        "INNER JOIN offertype ON marketoffer.offerTypeId = offertype.id " +
                        "INNER JOIN item ON marketoffer.itemId = item.id " +
                        "INNER JOIN attackStyle ON item.attackStyleId = attackStyle.id " +
                        "WHERE offertype.type LIKE 'SELL' AND item.name LIKE ? AND marketoffer.userId NOT LIKE ?";
        try
        {
            con = DriverManager.getConnection(sqlDatabase, sqlUsername, sqlPassword);
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, "%" + searchQuery + "%");
            stmt.setInt(2, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                offers.add(new MarketOffer(rs.getInt("id"), rs.getInt("userId"), rs.getInt("price"),
                        new Item(rs.getInt("itemId"), rs.getInt("level"), AttackStyle.valueOf(rs.getString("attackStyleType").toUpperCase()), rs.getString("name"), rs.getInt("health"), rs.getString("obtainDate")),
                        MarketOfferType.valueOf(rs.getString("type").toUpperCase())));
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
        return offers;
    }

    @Override
    public ArrayList<User> TEST(int id)
    {
        ArrayList<User> offers = new ArrayList<>();
        String query =
                "SELECT * FROM user WHERE user.id LIKE ?";
        try
        {
            con = DriverManager.getConnection(sqlDatabase, sqlUsername, sqlPassword);
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                offers.add(new User(rs.getString("name"), rs.getString("password"), rs.getInt("level"), rs.getInt("id"), rs.getInt("coins")));
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
        return offers;
    }

    @Override
    public ArrayList<MarketOffer> getSellingOffers()
    {
        ArrayList<MarketOffer> offers = new ArrayList<>();
        String query =
                "SELECT marketoffer.id AS id, marketoffer.userId AS userId, marketoffer.price AS price, offertype.type AS type, item.id AS itemId, item.level AS level, attackStyle.Type AS attackStyleType, item.name AS name, item.health AS health, item.obtainDate AS obtainDate FROM marketoffer " +
                        "INNER JOIN offertype ON marketoffer.offerTypeId = offertype.id " +
                        "INNER JOIN item ON marketoffer.itemId = item.id " +
                        "INNER JOIN attackStyle ON item.attackStyleId = attackStyle.id " +
                        "WHERE offertype.type LIKE 'SELL'";
        try
        {
            con = DriverManager.getConnection(sqlDatabase, sqlUsername, sqlPassword);
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                offers.add(new MarketOffer(rs.getInt("id"), rs.getInt("userId"), rs.getInt("price"),
                        new Item(rs.getInt("itemId"), rs.getInt("level"), AttackStyle.valueOf(rs.getString("attackStyleType").toUpperCase()), rs.getString("name"), rs.getInt("health"), rs.getString("obtainDate")),
                        MarketOfferType.valueOf(rs.getString("type").toUpperCase())));
            }
            closeConnection(con);
            return offers;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            closeConnection(con);
        }
        return offers;
    }

    @Override
    public boolean buyItem(MarketOffer offer, int buyerId)
    {
        String queryUpdateUser =
                "UPDATE item SET item.userId = ?, item.onMarket = 0 " +
                "WHERE item.id LIKE ?";
        String queryUpdateCoinsSeller =
                "UPDATE user SET user.coins = (user.coins + ?) " +
                "WHERE user.id LIKE ?";
        String queryUpdateCoinsBuyer =
                "UPDATE user SET user.coins = (user.coins - ?) " +
                "WHERE user.id LIKE ?";
        try
        {
            con = DriverManager.getConnection(sqlDatabase, sqlUsername, sqlPassword);
            PreparedStatement stmt = con.prepareStatement(queryUpdateUser);
            stmt.setInt(1, buyerId);
            stmt.setInt(2, offer.getItem().getId());
            stmt.executeUpdate();
            stmt = con.prepareStatement(queryUpdateCoinsSeller);
            stmt.setInt(1, offer.getPrice());
            stmt.setInt(2, offer.getUserId());
            stmt.executeUpdate();
            stmt = con.prepareStatement(queryUpdateCoinsBuyer);
            stmt.setInt(1, offer.getPrice());
            stmt.setInt(2, buyerId);
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