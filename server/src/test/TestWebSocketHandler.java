package test;

import java.net.Socket;

import lib.http.Request;
import lib.websocket.WebSocketHandler;
import lib.websocket.WebSocketUtils;

public class TestWebSocketHandler implements WebSocketHandler {
  @Override
  public boolean authorizeConnection(Socket socket, Request request) {
    String authCookie = request.getCookie("auth");
    if ((authCookie != null) && authCookie.equals("123")) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public void onConnect(Socket socket) {

  }

  @Override
  public void onMessage(Socket socket, String message) {
    System.out.println(message);
    try {
      WebSocketUtils.sendWebsocketMessage(socket.getOutputStream(), message);
    } catch (Exception e) {

    }
  }

  @Override
  public void onClose(Socket socket) {

  }
}
