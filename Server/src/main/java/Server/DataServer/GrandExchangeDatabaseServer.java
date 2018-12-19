package Server.DataServer;

import Server.SharedClientModels.Item;
import Server.SharedClientModels.MarketOffer;
import Server.SharedClientModels.User;
import Server.SharedClientModels.Weapon;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.util.ArrayList;

public class GrandExchangeDatabaseServer implements IGrandExchangeDatabaseServer
{
    private String serverLocation = "http://localhost:8090/GrandExchangeRestController";

    @Override
    public User login(User user)
    {
        HttpPost httpPost = new HttpPost(serverLocation + "/login");
        httpPost.addHeader("content-type", "application/json");
        try
        {
            httpPost.setEntity(new StringEntity(new Gson().toJson(user)));
            User authenticatedUser = new Gson().fromJson(EntityUtils.toString(HttpClients.createDefault().execute(httpPost).getEntity()), User.class);
            if (authenticatedUser != null)
            {
                user.setLoggedIn(true);
                System.out.println("Login Successfully.");
                return authenticatedUser;
            }
            System.out.println("Login failed!");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void register(User user)
    {

    }

    @Override
    public ArrayList<MarketOffer> getSellingOffers()
    {
        HttpGet httpGet = new HttpGet(serverLocation + "/getSellingOffers");
        httpGet.addHeader("content-type", "application/json");
        try
        {
            return new Gson().fromJson(EntityUtils.toString(HttpClients.createDefault().execute(httpGet).getEntity()), new TypeToken<ArrayList<MarketOffer>>(){}.getType());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean sellItem(MarketOffer offer)
    {
        return false;
    }

    @Override
    public ArrayList<Item> getBackPackItems(int userId)
    {
        HttpGet httpGet = new HttpGet(serverLocation + "/getBackPackItems/" + userId);
        httpGet.addHeader("content-type", "application/json");
        try
        {
            return new Gson().fromJson(EntityUtils.toString(HttpClients.createDefault().execute(httpGet).getEntity()), new TypeToken<ArrayList<Item>>(){}.getType());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void addItemToBackPack(Item item, int userId)
    {
        ArrayList<Object> postObjects = new ArrayList<>();
        postObjects.add(item);
        postObjects.add(userId);
        HttpPost httpPost = new HttpPost(serverLocation + "/addItemToBackPack");
        httpPost.addHeader("content-type", "application/json");
        try
        {
            httpPost.setEntity(new StringEntity(new Gson().toJson(postObjects)));
            HttpClients.createDefault().execute(httpPost);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean deleteItemFromBackPack(Item item, int userId)
    {
        HttpDelete httpDelete = new HttpDelete(serverLocation + "/deleteItemFromBackPack/" + Integer.toString(item.getId()) + "/" + Integer.toString(userId));
        httpDelete.addHeader("content-type", "application/json");
        try
        {
            boolean success = Boolean.parseBoolean(EntityUtils.toString(HttpClients.createDefault().execute(httpDelete).getEntity()));
            if (success)
            {
                System.out.println("Delete Successfully.");
                return true;
            }
            System.out.println("Delete failed!");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public ArrayList<MarketOffer> getMarketOffers(int userId)
    {
        HttpGet httpGet = new HttpGet(serverLocation + "/getMarketOffers/" + userId);
        httpGet.addHeader("content-type", "application/json");
        try
        {
            return new Gson().fromJson(EntityUtils.toString(HttpClients.createDefault().execute(httpGet).getEntity()), new TypeToken<ArrayList<MarketOffer>>(){}.getType());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
}