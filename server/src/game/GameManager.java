package game;

import java.util.HashMap;

import game.events.EventManager;
import game.events.GameCreateEvent;

public class GameManager {
  // <ID, Game>
  private HashMap<String, Game> games = new HashMap<>();
  public EventManager eventManager = new EventManager();

  public GameManager() {

  }

  public Game createGame() {
    // Todo: make sure game ids are unique
    Game game = new Game();
    games.put(game.getId(), game);
    eventManager.emitEvent(GameCreateEvent.class, new GameCreateEvent(this, game));
    return game;
  }

  public EventManager getEventManager() {
    return this.eventManager;
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

  public Game getGame(Player player) {
    return getGameByPlayerId(player.getId());
  }
}
