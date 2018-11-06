package Server.DataServer;

import Server.SharedClientModels.User;
import com.google.gson.Gson;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class GrandExchangeDatabaseServer implements IGrandExchangeDatabaseServer
{
    private String serverLocation = "http://localhost:8090/GrandExchangeRestController";

    @Override
    public boolean login(User user)
    {
        HttpPost httpPost = new HttpPost(serverLocation + "/login");
        httpPost.addHeader("content-type", "application/json");
        try
        {
            httpPost.setEntity(new StringEntity(new Gson().toJson(user)));
            if (EntityUtils.toString(HttpClients.createDefault().execute(httpPost).getEntity()).equals("success"))
            {
                user.setLoggedIn(true);
                System.out.println("Login Successfully.");
                return true;
            }
            System.out.println("Login failed!");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public void register(User user)
    {

    }
}