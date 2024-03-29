package game.events;

import game.Game;

/**
 * Represents an event where a game starts.
 */
public class GameStartEvent implements Event {
  private final Game game;

  /**
   * Creates a new game start event.
   * 
   * @param game The game.
   */
  public GameStartEvent(Game game) {
    this.game = game;
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
