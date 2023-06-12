import game.Game;
import game.GameManager;
import game.Operator;
import game.Question;
import game.http.CreateGame;
import game.http.Join;
import game.http.SetName;
import game.websocket.OperatorWebSocketHandler;
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

      WebSocketHandler operatorWebSocketHandler = new OperatorWebSocketHandler(gameManager);
      WebSocketServer operatorWebSocketServer = new WebSocketServer(operatorWebSocketHandler);

      server.addHandler("/join", RequestType.POST, new Join(gameManager));
      server.addHandler("/setName", RequestType.POST, new SetName(gameManager));
      server.addHandler("/createGame", RequestType.GET, new CreateGame(gameManager));
      server.addHandler("/wsPlayer", RequestType.GET, playerWebSocketServer);
      server.addHandler("/wsOperator", RequestType.GET, operatorWebSocketServer);

      System.out.println("Listening to port 80!");
    } catch (Exception e) {
      System.out.println("Exception");
    }

    Question[] questions = new Question[] {
        new Question(
            "What is your favourite color",
            new String[] { "A", "B", "C", "D" },
            0,
            30000)
    };
    Game game = gameManager.createGame(new Operator(null), questions);
  }
}
