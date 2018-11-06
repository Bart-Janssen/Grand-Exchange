package DataServer.Connector;

import DataServer.DataServerLogic.IDataServerLogic;
import DataServer.Factory.DatabaseServerFactory;
import DataServer.Models.DatabaseType;
import DataServer.SharedServerModels.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/GrandExchangeRestController")
public class RestController
{
    private IDataServerLogic logic = DatabaseServerFactory.getInstance().makeNewServerLogic(DatabaseType.HashMapDatabase);

    @POST
    @Path("/login")
    @Consumes("application/json")
    @Produces("application/json")
    public Response login(User user)
    {
        if (user == null) return Response.status(400).entity("400").build();
        System.out.println("[" + user.getUsername() + "]");
        return logic.login(user) ? Response.status(200).entity("success").build() : Response.status(400).entity("error").build();
    }

    @GET
    @Path("/get/{data}")
    @Produces("application/json")
    public Response get(@PathParam("data") String data)
    {
        if (data == null) return Response.status(400).entity("400").build();
        System.out.println("hai");
        System.out.println("[" + data + "]");
        return Response.status(200).entity("200").build();
    }
}