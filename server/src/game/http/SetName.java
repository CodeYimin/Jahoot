package game.http;

import java.net.Socket;

import game.GameManager;
import game.Player;
import lib.http.Request;
import lib.http.RequestHandler;
import lib.http.Response;
import lib.http.ResponseStatus;

public class SetName implements RequestHandler {
  private GameManager gameManager;

  public SetName(GameManager gameManager) {
    this.gameManager = gameManager;
  }

  @Override
  public void onRequest(Socket socket, Request request) {
    String playerId = request.getCookie("playerId");
    Player player = gameManager.getPlayer(playerId);

    if (player == null) {
      try {
        new Response(ResponseStatus.UNAUTHORIZED).send(socket.getOutputStream());
      } catch (Exception e) {
        e.printStackTrace();
      }
      return;
    }

    if (player.getName() != null) {
      try {
        new Response(ResponseStatus.FORBIDDEN).send(socket.getOutputStream());
      } catch (Exception e) {
        e.printStackTrace();
      }
      return;
    }

    String name = request.getBody();
    player.setName(name);

    try {
      new Response(ResponseStatus.OK).send(socket.getOutputStream());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
