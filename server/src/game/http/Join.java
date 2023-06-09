package game.http;

import java.net.Socket;

import game.Game;
import game.GameManager;
import game.Player;
import lib.http.Request;
import lib.http.RequestHandler;
import lib.http.Response;
import lib.http.ResponseStatus;

public class Join implements RequestHandler {
  private GameManager gameManager;

  public Join(GameManager gameManager) {
    this.gameManager = gameManager;
  }

  @Override
  public void onRequest(Socket socket, Request request) {
    String gameId = request.getBody();
    Game game = gameManager.getGame(gameId);

    if (game == null) {
      try {
        new Response(ResponseStatus.NOT_FOUND).send(socket.getOutputStream());
      } catch (Exception e) {

      }
      return;
    }

    Player player = game.createPlayer();
    Response response = new Response(ResponseStatus.OK);
    response.addHeader("Set-Cookie", "playerId=" + player.getId());

    try {
      response.send(socket.getOutputStream());
    } catch (Exception e) {

    }
  }
}
