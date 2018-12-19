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

    @POST
    @Path("/login")
    @Consumes("application/json")
    @Produces("application/json")
    public Response login(User user)
    {
        if (user == null) return Response.status(400).entity("400").build();
        User authenticatedUser = logic.login(user);
        return authenticatedUser != null ? Response.status(200).entity(new Gson().toJson(authenticatedUser)).build() : Response.status(400).entity("error").build();
    }

    @GET
    @Path("/getSellingOffers")
    @Consumes("application/json")
    public Response getSellingOffers()
    {
        return Response.status(200).entity(new Gson().toJson(getList())).build();//TODO: moet naar database itself
    }

    private ArrayList<MarketOffer> getList()
    {
        ArrayList<MarketOffer> sellOffers = new ArrayList<>();
        sellOffers.add(new MarketOffer(1, 496360, new Item(1, 80, AttackStyle.MELEE, "Sword"), MarketOfferType.SELL));
        sellOffers.add(new MarketOffer(1, 507599, new Item(1, 165, AttackStyle.MAGIC, "Staff"), MarketOfferType.SELL));
        sellOffers.add(new MarketOffer(1, 850850, new Item(1, 75, AttackStyle.RANGED, "Bow"), MarketOfferType.SELL));
        sellOffers.add(new MarketOffer(1, 39853, new Item(1, 65, AttackStyle.MELEE, "Sword"), MarketOfferType.SELL));
        sellOffers.add(new MarketOffer(1, 163575, new Item(1, 40, AttackStyle.MELEE, "Sword"), MarketOfferType.SELL));
        sellOffers.add(new MarketOffer(1, 488, new Item(1, 3, AttackStyle.MAGIC, "Staff"), MarketOfferType.SELL));
        sellOffers.add(new MarketOffer(1, 618011, new Item(1, 53, AttackStyle.RANGED, "Bow"), MarketOfferType.SELL));
        sellOffers.add(new MarketOffer(1, 900300, new Item(1, 200, AttackStyle.RANGED, "Bow"), MarketOfferType.SELL));
        sellOffers.add(new MarketOffer(1, 90904, new Item(1, 68, AttackStyle.MAGIC, "Staff"), MarketOfferType.SELL));
        sellOffers.add(new MarketOffer(1, 301321, new Item(1, 33, AttackStyle.MAGIC, "Staff"), MarketOfferType.SELL));
        return sellOffers;
    }

    @PUT
    @Path("/sellItem/")
    @Consumes("application/json")
    @Produces("application/json")
    public Response sellItem(String postObjectAsString)
    {
        MarketOffer offer = new Gson().fromJson(postObjectAsString, MarketOffer.class);
        return logic.sellItem(offer) ? Response.status(200).entity("Success").build() : Response.status(400).entity("Failed").build();
    }

    @DELETE
    @Path("/cancelOffer/{marketOfferIdAsString}/")
    @Consumes("application/json")
    @Produces("application/json")
    public Response cancelOffer(@PathParam("marketOfferIdAsString") String marketOfferIdAsString)
    {
        return logic.cancelOffer(Integer.parseInt(marketOfferIdAsString)) ? Response.status(200).entity("Success").build() : Response.status(200).entity("Failed").build();
    }

    @GET
    @Path("/getBackPackItems/{idAsString}")
    @Consumes("application/json")
    public Response getBackPackItems(@PathParam("idAsString") String idAsString)
    {
        int id = Integer.parseInt(idAsString);
        ArrayList<Item> items = logic.getBackPackItems(id);
        return Response.status(200).entity(new Gson().toJson(items)).build();
    }

    @GET
    @Path("/getMarketOffers/{idAsString}")
    @Consumes("application/json")
    public Response getMarketOffers(@PathParam("idAsString") String idAsString)
    {
        int id = Integer.parseInt(idAsString);
        ArrayList<MarketOffer> items = logic.getMarketOffers(id);
        return Response.status(200).entity(new Gson().toJson(items)).build();
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
        return logic.addItemToBackPack(item, userId) ? Response.status(200).entity(new Gson().toJson("200")).build() : Response.status(400).entity("error").build();
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