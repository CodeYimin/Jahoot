package game.events;

import game.Game;
import game.GameManager;

/**
 * Represents an event where a game is created.
 */
public class GameCreateEvent implements Event {
  private GameManager gameManager;
  private Game game;

  /**
   * Creates a new game create event.
   * 
   * @param gameManager The game manager.
   * @param game        The game.
   */
  public GameCreateEvent(GameManager gameManager, Game game) {
    this.gameManager = gameManager;
    this.game = game;
  }

  /**
   * Gets the game manager.
   * 
   * @return The game manager.
   */
  public GameManager getGameManager() {
    return this.gameManager;
  }

  /**
   * Gets the game.
   * 
   * @return The game.
   */
  public Game getGame() {
    return this.game;
  }
}
