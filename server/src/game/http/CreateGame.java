package game.http;

import java.net.Socket;

import game.Game;
import game.GameManager;
import game.Operator;
import game.Question;
import lib.http.Request;
import lib.http.RequestHandler;
import lib.http.Response;
import lib.http.ResponseStatus;

public class CreateGame implements RequestHandler {
  private GameManager gameManager;

  public CreateGame(GameManager gameManager) {
    this.gameManager = gameManager;
  }

  @Override
  public void onRequest(Socket socket, Request request) {
    String operatorId = String.format("%08d", (int) (Math.random() * 100000000));
    Operator operator = new Operator(operatorId);

    Question[] questions = new Question[] {
        new Question(
            "What is your favourite color",
            new String[] { "A", "B", "C", "D" },
            0,
            30000)
    };
    Game game = gameManager.createGame(operator, questions);

    Response response = new Response(ResponseStatus.OK);
    response.addHeader("Set-Cookie", "operatorId=" + operator.getId());
    response.setBody("{ \"id\": \"" + game.getId() + "\" }");

    try {
      response.send(socket.getOutputStream());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
