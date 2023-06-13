package lib.websocket;

import java.net.Socket;

import lib.http.Request;

/**
 * Handles WebSocket connections.
 */
public interface WebSocketHandler {
  /**
   * Authorizes a WebSocket connection.
   * 
   * @param socket  The socket that is connecting.
   * @param request The HTTP request that is being upgraded to a WebSocket
   *                connection.
   * @return Whether the connection is authorized.
   */
  public boolean authorizeConnection(Socket socket, Request request);

  /**
   * Called when a WebSocket connection is established.
   * 
   * @param socket The socket that is connecting.
   */
  public void onConnect(Socket socket);

  /**
   * Called when a WebSocket message is received.
   * 
   * @param socket  The socket that sent the message.
   * @param message The message that was sent.
   */
  public void onMessage(Socket socket, String message);

  /**
   * Called when a WebSocket connection is closed.
   * 
   * @param socket The socket that is closing.
   */
  public void onClose(Socket socket);
}
