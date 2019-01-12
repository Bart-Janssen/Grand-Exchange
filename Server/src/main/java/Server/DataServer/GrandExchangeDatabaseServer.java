package Server.DataServer;

import Server.Models.Logger;
import Server.SharedClientModels.Item;
import Server.SharedClientModels.MarketOffer;
import Server.SharedClientModels.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.util.ArrayList;

public class GrandExchangeDatabaseServer implements IGrandExchangeDatabaseServer
{
    private String serverLocation = "http://localhost:8090/GrandExchangeRestController";
    private static final String CONTENT_TYPE  = "content-type";
    private static final String APPLICATION_JSON = "application/json";

    @Override
    public User login(User user)
    {
        HttpPost httpPost = new HttpPost(serverLocation + "/login");
        httpPost.addHeader(CONTENT_TYPE, APPLICATION_JSON);
        try
        {
            httpPost.setEntity(new StringEntity(new Gson().toJson(user)));
            User authenticatedUser = new Gson().fromJson(EntityUtils.toString(HttpClients.createDefault().execute(httpPost).getEntity()), User.class);
            if (authenticatedUser != null)
            {
                user.setLoggedIn(true);
                return authenticatedUser;
            }
        }
        catch (Exception ex)
        {
            new Logger().log(ex);
            return null;
        }
        return null;
    }

    @Override
    public String register(User user)
    {
        HttpPost httpPost = new HttpPost(serverLocation + "/register");
        httpPost.addHeader(CONTENT_TYPE, APPLICATION_JSON);
        try
        {
            httpPost.setEntity(new StringEntity(new Gson().toJson(user)));
            return EntityUtils.toString(HttpClients.createDefault().execute(httpPost).getEntity());
        }
        catch (Exception ex)
        {
            new Logger().log(ex);
            return "Registering failed";
        }
    }

    @Override
    public ArrayList<MarketOffer> getSellingOffers()
    {
        HttpGet httpGet = new HttpGet(serverLocation + "/getSellingOffers");
        httpGet.addHeader(CONTENT_TYPE, APPLICATION_JSON);
        try
        {
            return new Gson().fromJson(EntityUtils.toString(HttpClients.createDefault().execute(httpGet).getEntity()), new TypeToken<ArrayList<MarketOffer>>(){}.getType());
        }
        catch (Exception ex)
        {
            new Logger().log(ex);
        }
        return null;
    }

    @Override
    public boolean sellItem(MarketOffer offer)
    {
        HttpPut httpPut = new HttpPut(serverLocation + "/sellItem");
        httpPut.addHeader(CONTENT_TYPE, APPLICATION_JSON);
        try
        {
            httpPut.setEntity(new StringEntity(new Gson().toJson(offer)));
            if (EntityUtils.toString(HttpClients.createDefault().execute(httpPut).getEntity()).equals("Success")) return true;
        }
        catch (Exception ex)
        {
            new Logger().log(ex);
        }
        return false;
    }

    @Override
    public boolean buyItem(MarketOffer marketOffer, int buyerId)
    {
        ArrayList<Object> postObjects = new ArrayList<>();
        postObjects.add(marketOffer);
        postObjects.add(buyerId);
        HttpPut httpPut = new HttpPut(serverLocation + "/buyItem");
        httpPut.addHeader(CONTENT_TYPE, APPLICATION_JSON);
        try
        {
            httpPut.setEntity(new StringEntity(new Gson().toJson(postObjects)));
            if (EntityUtils.toString(HttpClients.createDefault().execute(httpPut).getEntity()).equals("Success")) return true;
        }
        catch (Exception ex)
        {
            new Logger().log(ex);
        }
        return false;
    }

    @Override
    public boolean cancelOffer(MarketOffer offer)
    {
        HttpDelete httpDelete = new HttpDelete(serverLocation + "/cancelOffer/" + offer.getId());
        httpDelete.addHeader(CONTENT_TYPE, APPLICATION_JSON);
        try
        {
            if (EntityUtils.toString(HttpClients.createDefault().execute(httpDelete).getEntity()).equals("Success")) return true;
        }
        catch (Exception ex)
        {
            new Logger().log(ex);
        }
        return false;
    }

    @Override
    public ArrayList<MarketOffer> getSearchOffers(String searchQuery, int userId)
    {
        HttpGet httpGet = new HttpGet(serverLocation + "/getSearchOffers/" + searchQuery + "/" + userId);
        httpGet.addHeader(CONTENT_TYPE, APPLICATION_JSON);
        try
        {
            return new Gson().fromJson(EntityUtils.toString(HttpClients.createDefault().execute(httpGet).getEntity()), new TypeToken<ArrayList<MarketOffer>>(){}.getType());
        }
        catch (Exception ex)
        {
            new Logger().log(ex);
        }
        return null;
    }

    @Override
    public int getUserCoins(int id)
    {
        HttpGet httpGet = new HttpGet(serverLocation + "/getUserCoins/" + id);
        httpGet.addHeader(CONTENT_TYPE, APPLICATION_JSON);
        try
        {
            return new Gson().fromJson(EntityUtils.toString(HttpClients.createDefault().execute(httpGet).getEntity()), Integer.class);
        }
        catch (Exception ex)
        {
            new Logger().log(ex);
        }
        return -1;
    }

    @Override
    public ArrayList<Item> getBackPackItems(int userId)
    {
        HttpGet httpGet = new HttpGet(serverLocation + "/getBackPackItems/" + userId);
        httpGet.addHeader(CONTENT_TYPE, APPLICATION_JSON);
        try
        {
            return new Gson().fromJson(EntityUtils.toString(HttpClients.createDefault().execute(httpGet).getEntity()), new TypeToken<ArrayList<Item>>(){}.getType());
        }
        catch (Exception ex)
        {
            new Logger().log(ex);
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
        httpPost.addHeader(CONTENT_TYPE, APPLICATION_JSON);
        try
        {
            httpPost.setEntity(new StringEntity(new Gson().toJson(postObjects)));
            HttpClients.createDefault().execute(httpPost);
        }
        catch (Exception ex)
        {
            new Logger().log(ex);
        }
    }

    @Override
    public boolean deleteItemFromBackPack(Item item, int userId)
    {
        HttpDelete httpDelete = new HttpDelete(serverLocation + "/deleteItemFromBackPack/" + Integer.toString(item.getId()) + "/" + Integer.toString(userId));
        httpDelete.addHeader(CONTENT_TYPE, APPLICATION_JSON);
        try
        {
            if (Boolean.parseBoolean(EntityUtils.toString(HttpClients.createDefault().execute(httpDelete).getEntity()))) return true;
        }
        catch (Exception ex)
        {
            new Logger().log(ex);
        }
        return false;
    }

    @Override
    public ArrayList<MarketOffer> getMarketOffers(int userId)
    {
        HttpGet httpGet = new HttpGet(serverLocation + "/getMarketOffers/" + userId);
        httpGet.addHeader(CONTENT_TYPE, APPLICATION_JSON);
        try
        {
            return new Gson().fromJson(EntityUtils.toString(HttpClients.createDefault().execute(httpGet).getEntity()), new TypeToken<ArrayList<MarketOffer>>(){}.getType());
        }
        catch (Exception ex)
        {
            new Logger().log(ex);
        }
        return null;
    }
}