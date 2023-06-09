import game.Game;
import game.GameManager;
import game.http.Join;
import game.http.SetName;
import lib.http.HTTPServer;
import lib.http.RequestType;
import lib.websocket.WebSocketHandler;
import lib.websocket.WebSocketServer;
import test.TestWebSocketHandler;

public class Main {
  public static void main(String[] args) {
    GameManager gameManager = new GameManager();
    Game game = gameManager.createGame();
    System.out.println(game.getId());

    try {
      HTTPServer server = new HTTPServer(5000);

      WebSocketHandler webSocketHandler = new TestWebSocketHandler();
      WebSocketServer webSocketServer = new WebSocketServer(webSocketHandler);

      // server.addHandler("/hi", new TestRequestHandler());
      // server.addHandler("/ws", webSocketServer);
      server.addHandler("/join", RequestType.POST, new Join(gameManager));
      server.addHandler("/setName", RequestType.POST, new SetName(gameManager));

      System.out.println("Listening to port 5000!");
    } catch (Exception e) {
      System.out.println("Exception");
    }
  }
}
