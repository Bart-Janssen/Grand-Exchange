package Server.Connector;

import Server.Connector.Connection;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

public class Main
{
    public static void main(String[] args)
    {
        startWebSocketServer();
    }

    private static void startWebSocketServer()
    {
        Server webSocketServer = new Server();
        ServerConnector connector = new ServerConnector(webSocketServer);
        connector.setPort(6666);
        webSocketServer.addConnector(connector);
        ServletContextHandler webSocketContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
        webSocketContext.setContextPath("/");
        webSocketServer.setHandler(webSocketContext);
        try
        {
            WebSocketServerContainerInitializer.configureContext(webSocketContext).addEndpoint(Connection.class);
            webSocketServer.start();
            webSocketServer.join();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}