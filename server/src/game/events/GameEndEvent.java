package game.events;

import game.Game;

/**
 * Represents an event where a game ends.
 */
public class GameEndEvent implements Event {
  private final Game game;

  /**
   * Creates a new game end event.
   * 
   * @param game The game.
   */
  public GameEndEvent(Game game) {
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
