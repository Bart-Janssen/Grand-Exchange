package sample.WebSocketConnection;

import sample.Models.User;

public interface IWebSocketConnection
{
    void login(User message);
}