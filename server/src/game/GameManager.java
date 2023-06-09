package game;

import java.util.HashMap;

public class GameManager {
  // <ID, Game>
  private HashMap<String, Game> games = new HashMap<>();

  public GameManager() {

  }

  public Game createGame() {
    // Todo: make sure game ids are unique
    Game game = new Game();
    games.put(game.getId(), game);
    return game;
  }

  public Game getGame(String id) {
    return games.get(id);
  }

  public Player getPlayer(String id) {
    for (Game game : games.values()) {
      Player player = game.getPlayer(id);
      if (player != null) {
        return player;
      }
    }

    return null;
  }

  public Game getGameByPlayerId(String id) {
    for (Game game : games.values()) {
      if (game.getPlayer(id) != null) {
        return game;
      }
    }

    return null;
  }
}
