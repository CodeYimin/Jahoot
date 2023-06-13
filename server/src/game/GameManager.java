package game;

import java.util.HashMap;

import game.events.EventManager;
import game.events.GameCreateEvent;

/**
 * A manager that manages all games.
 */
public class GameManager {
  private HashMap<String, Game> games = new HashMap<>(); // <ID, Game>
  public EventManager eventManager = new EventManager();

  /**
   * Creates a new game.
   * 
   * @param operator  The operator of the game.
   * @param questions The questions of the game.
   * @return The created game.
   */
  public Game createGame(Operator operator, Question[] questions) {
    String id = String.format("%04d", (int) (Math.random() * 10000));
    Game game = new Game(id, operator, questions);
    games.put(game.getId(), game);
    eventManager.emitEvent(GameCreateEvent.class, new GameCreateEvent(this, game));
    return game;
  }

  /**
   * Gets the event manager.
   * 
   * @return The event manager.
   */
  public EventManager getEventManager() {
    return this.eventManager;
  }

  /**
   * Gets the game with the specified ID.
   * 
   * @param id The ID of the game.
   * @return The game with the specified ID.
   */
  public Game getGame(String id) {
    return games.get(id);
  }

  /**
   * Gets the game with the specified operator.
   * 
   * @param operator The operator of the game.
   * @return The game with the specified operator.
   */
  public Game getGame(Operator operator) {
    for (Game game : games.values()) {
      if (game.getOperator().equals(operator)) {
        return game;
      }
    }

    return null;
  }

  /**
   * Gets the player with the specified ID.
   * 
   * @param id The ID of the player.
   * @return The player with the specified ID.
   */
  public Player getPlayer(String id) {
    for (Game game : games.values()) {
      Player player = game.getPlayer(id);
      if (player != null) {
        return player;
      }
    }

    return null;
  }

  /**
   * Gets the operator with the specified ID.
   * 
   * @param id The ID of the operator.
   * @return The operator with the specified ID.
   */
  public Operator getOperator(String id) {
    for (Game game : games.values()) {
      if (game.getOperator().getId().equals(id)) {
        return game.getOperator();
      }
    }

    return null;
  }

  /**
   * Gets the game with the specified player.
   * 
   * @param id The id of the player of the game.
   * @return The game with the specified player.
   */
  public Game getGameByPlayerId(String id) {
    for (Game game : games.values()) {
      if (game.getPlayer(id) != null) {
        return game;
      }
    }

    return null;
  }

  /**
   * Gets the game with the specified player.
   * 
   * @param player The player of the game.
   * @return The game with the specified player.
   */
  public Game getGame(Player player) {
    return getGameByPlayerId(player.getId());
  }
}
