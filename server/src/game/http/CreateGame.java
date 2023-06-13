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

/**
 * Handles HTTP requests to create a game.
 */
public class CreateGame implements RequestHandler {
  private GameManager gameManager;

  /**
   * Creates a new CreateGame.
   * 
   * @param gameManager The GameManager to use.
   */
  public CreateGame(GameManager gameManager) {
    this.gameManager = gameManager;
  }

  @Override
  public void onRequest(Socket socket, Request request) {
    String operatorId = String.format("%08d", (int) (Math.random() * 100000000));
    Operator operator = new Operator(operatorId);

    Question[] questions = new Question[] {
        new Question(
            "Is this game good?",
            new String[] { "No", "Yes", "Very yes", "No no" },
            2,
            5000),
        new Question(
            "Did this game give the creator depression?",
            new String[] { "Yes", "No" },
            0,
            5000),
        new Question(
            "Would I do this again?",
            new String[] { "Yes", "No", "Absolutely not" },
            2,
            5000),
        new Question(
            "What Mark should this game get?",
            new String[] { "0%", "20%", "40%", "60%", "80%", "100%" },
            5,
            15000),
        new Question(
            "Did you enjoy playing this game?",
            new String[] { "Yes", "No" },
            1,
            5000),
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
