package lib.websocket;

import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.net.Socket;

import lib.http.Request;
import lib.http.RequestHandler;
import lib.http.Response;
import lib.http.ResponseStatus;

/**
 * A WebSocket server that handles WebSocket connections and forwards messages
 * to a WebSocketHandler.
 */
public class WebSocketServer implements RequestHandler {
  private WebSocketHandler handler;

  /**
   * Creates a new WebSocketServer.
   * 
   * @param handler The WebSocketHandler that will handle WebSocket connections.
   */
  public WebSocketServer(WebSocketHandler handler) {
    this.handler = handler;
  }

  @Override
  public void onRequest(Socket socket, Request request) {
    try {
      // Get IO Streams
      BufferedInputStream input = new BufferedInputStream(socket.getInputStream());
      OutputStream output = socket.getOutputStream();

      // Authenticate request using handler
      if (!handler.authorizeConnection(socket, request)) {
        new Response(ResponseStatus.UNAUTHORIZED).send(output);
        return;
      }

      // Upgrade HTTP connection to create WebSocket connection
      WebSocketUtils.upgradeToWebsocket(output, request);
      handler.onConnect(socket);

      // Read WebSocket messages and forward them to the handler
      while (!socket.isClosed()) {
        String message = WebSocketUtils.readWebsocketMessage(input);
        handler.onMessage(socket, message);
      }

      // Socket is closed
      handler.onClose(socket);

    } catch (Exception e) {

    }
  }
}
