import game.Game;
import game.GameManager;
import game.http.Join;
import game.http.SetName;
import game.websocket.PlayerWebSocketHandler;
import lib.http.HTTPServer;
import lib.http.RequestType;
import lib.websocket.WebSocketHandler;
import lib.websocket.WebSocketServer;

public class Main {
  public static void main(String[] args) {
    GameManager gameManager = new GameManager();

    try {
      HTTPServer server = new HTTPServer(80);

      WebSocketHandler playerWebSocketHandler = new PlayerWebSocketHandler(gameManager);
      WebSocketServer playerWebSocketServer = new WebSocketServer(playerWebSocketHandler);

      server.addHandler("/join", RequestType.POST, new Join(gameManager));
      server.addHandler("/setName", RequestType.POST, new SetName(gameManager));
      server.addHandler("/wsPlayer", RequestType.GET, playerWebSocketServer);

      System.out.println("Listening to port 80!");
    } catch (Exception e) {
      System.out.println("Exception");
    }

    Game game = gameManager.createGame();
  }
}
