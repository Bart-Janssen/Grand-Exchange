package sample.WebSocketConnection;

import com.google.gson.Gson;
import sample.Models.MessageType;
import sample.Models.User;
import sample.Models.UserSession;
import sample.Models.WebSocketMessage;

public class WebSocketConnection implements IWebSocketConnection
{
    @Override
    public void login(User user)
    {
        WebSocketMessage webSocketMessage = new WebSocketMessage();
        webSocketMessage.setUser(user);
        webSocketMessage.setOperation(MessageType.LOGIN);
        String jsonMessage = new Gson().toJson(webSocketMessage);
        UserSession.getInstance().getSession().getAsyncRemote().sendText(jsonMessage);
    }
}