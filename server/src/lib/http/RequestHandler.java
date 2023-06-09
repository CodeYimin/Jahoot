package lib.http;

import java.net.Socket;

public interface RequestHandler {
  public abstract void onRequest(Socket socket, Request request);
}
