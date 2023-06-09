package lib.http;
/* [WebServerDemo.java]
 * Description: This is an example of a web server.
 * The program  waits for a client and accepts a message. 
 * It then responds to the message and quits.
 * This server demonstrates how to employ multithreading to accepts multiple clients
 * @author Mangat
 * @version 1.0a
 */

/* HOW-TO: Start this program and then use your web browser to go to
 * http://127.0.0.1:5000. When you see the inquiry appear in you JFrame
 * click the HTML button to send HTML back to the browser */

//imports for network communication
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class HTTPServer {
  // <request path + " " + type, request listener>
  private HashMap<String, RequestHandler> requestHandlers = new HashMap<>();

  public HTTPServer(int port) throws IOException {
    Thread serverSocketListener = new Thread(new ServerSocketListener(port));
    serverSocketListener.start();
  }

  public void addHandler(String path, RequestType type, RequestHandler handler) {
    requestHandlers.put(path + " " + type.toString(), handler);
  }

  private class ServerSocketListener implements Runnable {
    private ServerSocket serverSocket;

    public ServerSocketListener(int port) throws IOException {
      this.serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
      Socket client = null;

      try {
        while (true) {
          client = serverSocket.accept();
          System.out.println("Client connected");
          Thread t = new Thread(new RequestSocketHandler(client));
          t.start();
        }
      } catch (Exception e) {
        System.out.println("Error accepting connection");
        try {
          client.close();
        } catch (Exception e1) {
          System.out.println("Failed to close socket");
        }
      }
    }
  }

  private class RequestSocketHandler implements Runnable {
    private Socket socket;

    public RequestSocketHandler(Socket socket) {
      this.socket = socket;
    }

    @Override
    public void run() {
      Request request;
      try {
        request = new Request(new BufferedInputStream(this.socket.getInputStream()));
      } catch (Exception e) {
        e.printStackTrace();
        return;
      }

      RequestHandler handler = requestHandlers.get(request.getPath() + " " + request.getType().toString());
      if (handler != null) {
        handler.onRequest(socket, request);
      } else {
        Response notFoundResponse = new Response(ResponseStatus.NOT_FOUND);

        try {
          notFoundResponse.send(socket.getOutputStream());
        } catch (Exception e) {

        }
      }

      // if (!socket.isClosed()) {
      try {
        socket.close();
        System.out.println("Client closed");
      } catch (Exception e) {
        System.out.println("Failed to close socket");
        e.printStackTrace();
      }
      // }
    }
  }
}