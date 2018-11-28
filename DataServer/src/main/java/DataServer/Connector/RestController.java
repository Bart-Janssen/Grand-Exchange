package DataServer.Connector;

import DataServer.DataServerLogic.IDataServerLogic;
import DataServer.Factory.DatabaseServerFactory;
import DataServer.Models.DatabaseType;
import DataServer.SharedServerModels.*;
import com.google.gson.Gson;

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
        return logic.login(user) ? Response.status(200).entity("success").build() : Response.status(400).entity("error").build();
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
}