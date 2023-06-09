package game.websocket;

import java.net.Socket;

import lib.http.Request;
import lib.websocket.WebSocketHandler;

public class ConnectPlayer implements WebSocketHandler {
  @Override
  public boolean authorizeConnection(Socket socket, Request request) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'authenticateConnection'");
  }

  @Override
  public void onConnect(Socket socket) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'onConnect'");
  }

  @Override
  public void onMessage(Socket socket, String message) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'onMessage'");
  }

}
