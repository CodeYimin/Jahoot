package lib.websocket;

import java.net.Socket;

import lib.http.Request;

public interface WebSocketHandler {
  public boolean authorizeConnection(Socket socket, Request request);

  public void onConnect(Socket socket);

  public void onMessage(Socket socket, String message);

  public void onClose(Socket socket);
}
