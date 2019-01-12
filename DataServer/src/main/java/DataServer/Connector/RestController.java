package DataServer.Connector;

import DataServer.DataServerLogic.IDataServerLogic;
import DataServer.Factory.DatabaseServerFactory;
import DataServer.Models.DatabaseType;
import DataServer.SharedServerModels.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/GrandExchangeRestController")
public class RestController
{
    private IDataServerLogic logic = DatabaseServerFactory.getInstance().makeNewServerLogic(DatabaseType.MYSQL);
    private static final String ERROR = "error";
    private static final String SUCCESS = "Success";
    private static final String FAILED = "Failed";

    @POST
    @Path("/login")
    @Consumes("application/json")
    @Produces("application/json")
    public Response login(User user)
    {
        if (user == null) return Response.status(400).entity("400").build();
        User authenticatedUser = logic.login(user);
        return authenticatedUser != null ? Response.status(200).entity(new Gson().toJson(authenticatedUser)).build() : Response.status(400).entity(ERROR).build();
    }

    @POST
    @Path("/register")
    @Consumes("application/json")
    @Produces("application/json")
    public Response Register(User user)
    {
        if (user == null) return Response.status(400).entity("Registering failed").build();
        String message = logic.register(user);
        return Response.status(400).entity(message).build();
    }

    @GET
    @Path("/getUserCoins/{idAsString}/")
    @Consumes("application/json")
    public Response getUserCoins(@PathParam("idAsString") String idAsString)
    {
        int id = Integer.parseInt(idAsString);
        return Response.status(200).entity(new Gson().toJson(logic.getUserCoins(id))).build();
    }

    @GET
    @Path("/getSellingOffers/")
    @Consumes("application/json")
    public Response getSellingOffers()
    {
        return Response.status(200).entity(new Gson().toJson(logic.getSellingOffers())).build();
    }

    @PUT
    @Path("/sellItem/")
    @Consumes("application/json")
    @Produces("application/json")
    public Response sellItem(String postObjectAsString)
    {
        MarketOffer offer = new Gson().fromJson(postObjectAsString, MarketOffer.class);
        return logic.sellItem(offer) ? Response.status(200).entity(SUCCESS).build() : Response.status(400).entity(FAILED).build();
    }

    @PUT
    @Path("/buyItem/")
    @Consumes("application/json")
    @Produces("application/json")
    public Response buyItem(String postObjectsAsString)
    {
        ArrayList<Object> postObjects = new Gson().fromJson(postObjectsAsString, new TypeToken<ArrayList<Object>>(){}.getType());

        String offerAsJson = new Gson().toJson(postObjects.get(0));
        MarketOffer offer = new Gson().fromJson(offerAsJson, MarketOffer.class);
        String buyerIdAsJson = new Gson().toJson(postObjects.get(1));
        int buyerId = new Gson().fromJson(buyerIdAsJson, int.class);
        return logic.buyItem(offer, buyerId) ? Response.status(200).entity(SUCCESS).build() : Response.status(400).entity(FAILED).build();
    }

    @DELETE
    @Path("/cancelOffer/{marketOfferIdAsString}/")
    @Consumes("application/json")
    @Produces("application/json")
    public Response cancelOffer(@PathParam("marketOfferIdAsString") String marketOfferIdAsString)
    {
        return logic.cancelOffer(Integer.parseInt(marketOfferIdAsString)) ? Response.status(200).entity(SUCCESS).build() : Response.status(200).entity(FAILED).build();
    }

    @GET
    @Path("/getBackPackItems/{idAsString}")
    @Consumes("application/json")
    public Response getBackPackItems(@PathParam("idAsString") String idAsString)
    {
        int id = Integer.parseInt(idAsString);
        ArrayList<Item> items = logic.getBackPackItems(id);
        return items != null ? Response.status(200).entity(new Gson().toJson(items)).build() : Response.status(400).entity(ERROR).build();
    }

    @GET
    @Path("/getMarketOffers/{idAsString}")
    @Consumes("application/json")
    public Response getMarketOffers(@PathParam("idAsString") String idAsString)
    {
        int id = Integer.parseInt(idAsString);
        ArrayList<MarketOffer> items = logic.getMarketOffers(id);
        return items != null ? Response.status(200).entity(new Gson().toJson(items)).build() : Response.status(400).entity(ERROR).build();
    }

    @GET
    @Path("/getSearchOffers/{searchQuery}/{userIdAsString}")
    @Consumes("application/json")
    public Response getSearchOffers(@PathParam("searchQuery") String searchQuery, @PathParam("userIdAsString") String userIdAsString)
    {
        int userId = Integer.parseInt(userIdAsString);
        ArrayList<MarketOffer> items = logic.getSearchOffers(searchQuery, userId);
        return items != null ? Response.status(200).entity(new Gson().toJson(items)).build() : Response.status(400).entity(ERROR).build();
    }

    @POST
    @Path("/addItemToBackPack")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addItemToBackPack(String postObjectsAsString)
    {
        ArrayList<Object> postObjects = new Gson().fromJson(postObjectsAsString, new TypeToken<ArrayList<Object>>(){}.getType());
        String itemAsJson = new Gson().toJson(postObjects.get(0));
        Item item = new Gson().fromJson(itemAsJson, Item.class);
        String userIdAsJson = new Gson().toJson(postObjects.get(1));
        int userId = new Gson().fromJson(userIdAsJson, int.class);
        if (item == null) return Response.status(400).entity("400").build();
        return logic.addItemToBackPack(item, userId) ? Response.status(200).entity(new Gson().toJson("200")).build() : Response.status(400).entity(ERROR).build();
    }

    @DELETE
    @Path("/deleteItemFromBackPack/{itemIdAsString}/{userIdAsString}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response deleteItemFromBackPack(@PathParam("itemIdAsString") String itemIdAsString, @PathParam("userIdAsString") String userIdAsString)
    {
        int itemId = Integer.parseInt(itemIdAsString);
        int userId = Integer.parseInt(userIdAsString);
        return logic.deleteItemFromBackPack(itemId, userId) ? Response.status(200).entity("true").build() : Response.status(200).entity("false").build();
    }
}