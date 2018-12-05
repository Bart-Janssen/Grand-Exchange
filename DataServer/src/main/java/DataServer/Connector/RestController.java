package DataServer.Connector;

import DataServer.DataServerLogic.IDataServerLogic;
import DataServer.Factory.DatabaseServerFactory;
import DataServer.Models.DatabaseType;
import DataServer.SharedServerModels.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import sun.security.jgss.GSSCaller;

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
        sellOffers.add(new MarketOffer(496360, new Item(80, AttackStyle.MELEE, "Sword"), MarketOfferType.SELL, new User()));
        sellOffers.add(new MarketOffer(507599, new Item(165, AttackStyle.MAGIC, "Staff"), MarketOfferType.SELL, new User()));
        sellOffers.add(new MarketOffer(850850, new Item(75, AttackStyle.RANGED, "Bow"), MarketOfferType.SELL, new User()));
        sellOffers.add(new MarketOffer(39853, new Item(65, AttackStyle.MELEE, "Sword"), MarketOfferType.SELL, new User()));
        sellOffers.add(new MarketOffer(163575, new Item(40, AttackStyle.MELEE, "Sword"), MarketOfferType.SELL, new User()));
        sellOffers.add(new MarketOffer(488, new Item(3, AttackStyle.MAGIC, "Staff"), MarketOfferType.SELL, new User()));
        sellOffers.add(new MarketOffer(618011, new Item(53, AttackStyle.RANGED, "Bow"), MarketOfferType.SELL, new User()));
        sellOffers.add(new MarketOffer(900300, new Item(200, AttackStyle.RANGED, "Bow"), MarketOfferType.SELL, new User()));
        sellOffers.add(new MarketOffer(90904, new Item(68, AttackStyle.MAGIC, "Staff"), MarketOfferType.SELL, new User()));
        sellOffers.add(new MarketOffer(301321, new Item(33, AttackStyle.MAGIC, "Staff"), MarketOfferType.SELL, new User()));
        return sellOffers;
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

    @POST
    @Path("/addItemToBackPack")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addItemToBackPack(String postObjectsAsString)
    {
        ArrayList<Object> postObjects = new Gson().fromJson(postObjectsAsString, new TypeToken<ArrayList<Object>>(){}.getType());
        System.out.println("po0: " + postObjects.get(0).toString());
        System.out.println(new Gson().toJson(postObjects.get(0)));
        String h = new Gson().toJson(postObjects.get(0));

        Item item = new Gson().fromJson(h, Item.class);

        System.out.println("hai");
        String id = new Gson().toJson(postObjects.get(1));


        int userId = new Gson().fromJson(id, int.class);
        System.out.println("int");
        if (item == null) return Response.status(400).entity("400").build();
        return logic.addItemToBackPack(item, userId) ? Response.status(200).entity(new Gson().toJson("200")).build() : Response.status(400).entity("error").build();
    }
}