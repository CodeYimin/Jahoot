package game.websocket;

import java.net.Socket;
import java.util.HashMap;

import game.Game;
import game.GameManager;
import game.Player;
import game.events.EventListener;
import game.events.GameCreateEvent;
import game.events.GameStartEvent;
import lib.http.Request;
import lib.websocket.WebSocketHandler;
import lib.websocket.WebSocketUtils;

public class PlayerWebSocketHandler implements WebSocketHandler {
  private GameManager gameManager;
  private HashMap<Socket, Player> players = new HashMap<>();
  private HashMap<Player, Socket> sockets = new HashMap<>();

  public PlayerWebSocketHandler(GameManager gameManager) {
    this.gameManager = gameManager;
    gameManager.getEventManager().addListener(GameCreateEvent.class, new GameCreateEventListener());
  }

  @Override
  public boolean authorizeConnection(Socket socket, Request request) {
    String playerId = request.getCookie("playerId");
    if (playerId == null) {
      return false;
    }

    Player player = gameManager.getPlayer(playerId);
    if (player == null) {
      return false;
    }

    players.put(socket, player);
    sockets.put(player, socket);
    return true;
  }

  @Override
  public void onConnect(Socket socket) {
    Game game = gameManager.getGame(players.get(socket));
    game.start();
  }

  @Override
  public void onMessage(Socket socket, String message) {
    Player player = players.get(socket);
    System.out.println(player.getName());
  }

  @Override
  public void onClose(Socket socket) {
    sockets.remove(players.get(socket));
    players.remove(socket);
  }

  private class GameCreateEventListener implements EventListener<GameCreateEvent> {
    @Override
    public void onEvent(GameCreateEvent event) {
      Game game = event.getGame();
      game.getEventManager().addListener(GameStartEvent.class, new GameStartEventListener());
    }
  }

  private class GameStartEventListener implements EventListener<GameStartEvent> {
    @Override
    public void onEvent(GameStartEvent event) {
      Game game = event.getGame();
      for (Player player : game.getPlayers()) {
        Socket socket = sockets.get(player);
        try {
          WebSocketUtils.sendWebsocketMessage(socket.getOutputStream(), "Game started!");
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }
}
