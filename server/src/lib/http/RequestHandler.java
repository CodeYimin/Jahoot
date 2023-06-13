package lib.http;

import java.net.Socket;

/**
 * Handles HTTP requests.
 */
public interface RequestHandler {
  /**
   * Called when an HTTP request is received.
   * 
   * @param socket  The socket that sent the request.
   * @param request The request that was sent.
   */
  public abstract void onRequest(Socket socket, Request request);
}
