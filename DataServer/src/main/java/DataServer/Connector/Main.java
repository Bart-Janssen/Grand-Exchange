package DataServer.Connector;

import DataServer.Models.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

public class Main
{
    public static void main(String[] args)
    {
        startServer();
    }

    private static void startServer()
    {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        Server jettyServer = new Server(8090);
        jettyServer.setHandler(context);
        ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);
        jerseyServlet.setInitParameter("jersey.config.server.provider.classnames", RestController.class.getCanonicalName());
        try
        {
            jettyServer.start();
            jettyServer.join();
        }
        catch (Exception e)
        {
            new Logger().log(e);
        }
        finally
        {
            jettyServer.destroy();
        }
    }
}