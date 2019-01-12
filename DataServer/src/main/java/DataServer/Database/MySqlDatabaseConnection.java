package DataServer.Database;

import DataServer.Models.Logger;
import DataServer.SharedServerModels.*;
import org.mindrot.jbcrypt.BCrypt;

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

    private boolean verifyHash(String password, String hash)
    {
        return BCrypt.checkpw(password, hash);
    }

    public User login(User user)
    {
        String query = "SELECT * FROM user WHERE user.name LIKE ?";//" AND user.password LIKE ?";
        try (Connection connection = DriverManager.getConnection(sqlDatabase, sqlUsername, sqlPassword); PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1, user.getUsername());
            //statement.setString(2, user.getPassword());
            try (ResultSet resultSet = statement.executeQuery())
            {
                while (resultSet.next())
                {
                    if (verifyHash(user.getPassword(), resultSet.getString("password")))
                    {
                        return new User(resultSet.getString("name"), "", resultSet.getInt("level"), resultSet.getInt("id"), resultSet.getInt("coins"));
                    }
                }
            }
            return null;
        }
        catch(Exception e)
        {
            new Logger().log(e);
            return null;
        }
    }

    public String register(User user)
    {
        String query =
                "INSERT INTO user (name, password, level, coins) " +
                "VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(sqlDatabase, sqlUsername, sqlPassword); PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getLevel());
            statement.setInt(4, user.getCoins());
            statement.executeUpdate();
            return "Registered successfully";
        }
        catch(Exception e)
        {
            return e.getMessage().contains("Duplicate") ? "User already exists" : "Registering failed";
        }
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
        try (Connection connection = DriverManager.getConnection(sqlDatabase, sqlUsername, sqlPassword); PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery())
            {
                while (resultSet.next())
                {
                    items.add(new Item(resultSet.getInt("id"), resultSet.getInt("level"), AttackStyle.valueOf(resultSet.getString("type").toUpperCase()), resultSet.getString("name"), resultSet.getInt("health"), resultSet.getString("obtainDate")));
                }
            }
        }
        catch(Exception e)
        {
            new Logger().log(e);
        }
        return items;
    }

    @Override
    public boolean addItemToBackPack(Item item, int userId)
    {
        String query =
                "INSERT INTO item (name, obtainDate, health, level, attackStyleId, userId, onMarket) " +
                "VALUES (?, ?, ?, ?, (SELECT attackStyle.id FROM attackStyle WHERE attackStyle.type = ?), ?, 0) ";
        try (Connection connection = DriverManager.getConnection(sqlDatabase, sqlUsername, sqlPassword); PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1, item.getName());
            statement.setString(2, item.getObtainDate());
            statement.setInt(3, item.getItemHealth());
            statement.setInt(4, item.getItemLevel());
            statement.setString(5, item.getAttackStyle().toString());
            statement.setInt(6, userId);
            statement.executeUpdate();
            return true;
        }
        catch(Exception e)
        {
            new Logger().log(e);
            return false;
        }
    }

    @Override
    public boolean deleteItemFromBackPack(int itemId, int userId)
    {
        String query = "DELETE FROM item WHERE item.id LIKE ? AND item.UserId LIKE ?";
        try (Connection connection = DriverManager.getConnection(sqlDatabase, sqlUsername, sqlPassword); PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setInt(1, itemId);
            statement.setInt(2, userId);
            statement.executeUpdate();
        }
        catch(Exception e)
        {
            new Logger().log(e);
            return false;
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
        try (Connection connection = DriverManager.getConnection(sqlDatabase, sqlUsername, sqlPassword); PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery())
            {
                while (resultSet.next())
                {
                    offers.add(new MarketOffer(resultSet.getInt("id"), resultSet.getInt("userId"), resultSet.getInt("price"),
                            new Item(resultSet.getInt("itemId"), resultSet.getInt("level"), AttackStyle.valueOf(resultSet.getString("attackStyleType").toUpperCase()), resultSet.getString("name"), resultSet.getInt("health"), resultSet.getString("obtainDate")),
                            MarketOfferType.valueOf(resultSet.getString("type").toUpperCase())));
                }
            }
        }
        catch(Exception e)
        {
            new Logger().log(e);
        }
        return offers;
    }

    @Override
    public boolean sellItem(MarketOffer offer)
    {
        String query =
                "INSERT INTO marketoffer (price, userId, itemId, offerTypeId) " +
                "VALUES (?, ?, ?, (SELECT offertype.id FROM offertype WHERE offertype.type LIKE ?))";
        try (Connection connection = DriverManager.getConnection(sqlDatabase, sqlUsername, sqlPassword); PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setInt(1, offer.getPrice());
            statement.setInt(2, offer.getUserId());
            statement.setInt(3, offer.getItem().getId());
            statement.setString(4, offer.getType().toString());
            statement.executeUpdate();
            return true;
        }
        catch(Exception e)
        {
            new Logger().log(e);
            return false;
        }
    }

    @Override
    public boolean cancelOffer(int offerId)
    {
        String query = "DELETE FROM marketoffer WHERE marketoffer.id LIKE ?";
        try (Connection connection = DriverManager.getConnection(sqlDatabase, sqlUsername, sqlPassword); PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setInt(1, offerId);
            statement.executeUpdate();
            return true;
        }
        catch(Exception e)
        {
            new Logger().log(e);
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
                        "WHERE offertype.type LIKE 'SELL' AND item.name LIKE ? AND marketoffer.userId NOT LIKE ? AND item.onMarket LIKE 1";
        try (Connection connection = DriverManager.getConnection(sqlDatabase, sqlUsername, sqlPassword); PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1, "%" + searchQuery + "%");
            statement.setInt(2, userId);
            try (ResultSet resultSet = statement.executeQuery())
            {
                while (resultSet.next())
                {
                    offers.add(new MarketOffer(resultSet.getInt("id"), resultSet.getInt("userId"), resultSet.getInt("price"),
                            new Item(resultSet.getInt("itemId"), resultSet.getInt("level"), AttackStyle.valueOf(resultSet.getString("attackStyleType").toUpperCase()), resultSet.getString("name"), resultSet.getInt("health"), resultSet.getString("obtainDate")),
                            MarketOfferType.valueOf(resultSet.getString("type").toUpperCase())));
                }
            }
        }
        catch(Exception e)
        {
            new Logger().log(e);
        }
        return offers;
    }

    @Override
    public int getUserCoins(int id)
    {
        int coins = -1;
        String query =
                "SELECT coins FROM user WHERE user.id LIKE ?";
        try (Connection connection = DriverManager.getConnection(sqlDatabase, sqlUsername, sqlPassword); PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery())
            {
                if (resultSet.next()) coins = resultSet.getInt("coins");
            }
        }
        catch(Exception e)
        {
            new Logger().log(e);
        }
        return coins;
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
        try (Connection connection = DriverManager.getConnection(sqlDatabase, sqlUsername, sqlPassword); PreparedStatement statement = connection.prepareStatement(query))
        {
            try (ResultSet resultSet = statement.executeQuery())
            {
                while (resultSet.next())
                {
                    offers.add(new MarketOffer(resultSet.getInt("id"), resultSet.getInt("userId"), resultSet.getInt("price"),
                            new Item(resultSet.getInt("itemId"), resultSet.getInt("level"), AttackStyle.valueOf(resultSet.getString("attackStyleType").toUpperCase()), resultSet.getString("name"), resultSet.getInt("health"), resultSet.getString("obtainDate")),
                            MarketOfferType.valueOf(resultSet.getString("type").toUpperCase())));
                }
            }
            return offers;
        }
        catch(Exception e)
        {
            new Logger().log(e);
        }
        return offers;
    }

    @Override
    public boolean buyItem(MarketOffer offer, int buyerId)
    {
        try (Connection connection = DriverManager.getConnection(sqlDatabase, sqlUsername, sqlPassword))
        {
            updateBuyUpdateItem(connection, offer, buyerId);
            updateCoinsSeller(connection, offer);
            updateCoinsBuyer(connection, offer, buyerId);
            return true;
        }
        catch(Exception e)
        {
            new Logger().log(e);
            return false;
        }
    }

    private void updateBuyUpdateItem(Connection connection, MarketOffer offer, int buyerId)
    {
        String queryUpdateItem =
                "UPDATE item SET item.userId = ?, item.onMarket = 0 " +
                "WHERE item.id LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(queryUpdateItem))
        {
            statement.setInt(1, buyerId);
            statement.setInt(2, offer.getItem().getId());
            statement.executeUpdate();
        }
        catch (Exception ex)
        {
            new Logger().log(ex);
        }
    }

    private void updateCoinsSeller(Connection connection, MarketOffer offer)
    {
        String queryUpdateCoinsSeller =
                "UPDATE user SET user.coins = (user.coins + ?) " +
                "WHERE user.id LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(queryUpdateCoinsSeller))
        {
            statement.setInt(1, offer.getPrice());
            statement.setInt(2, offer.getUserId());
            statement.executeUpdate();
        }
        catch (Exception ex)
        {
            new Logger().log(ex);
        }
    }

    private void updateCoinsBuyer(Connection connection, MarketOffer offer, int buyerId)
    {
        String queryUpdateCoinsBuyer =
                "UPDATE user SET user.coins = (user.coins - ?) " +
                "WHERE user.id LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(queryUpdateCoinsBuyer))
        {
            statement.setInt(1, offer.getPrice());
            statement.setInt(2, buyerId);
            statement.executeUpdate();
        }
        catch (Exception ex)
        {
            new Logger().log(ex);
        }
    }
}