package game.events;

import game.Game;
import game.Player;

/**
 * Represents an event where a player joins a game.
 */
public class PlayerJoinEvent implements Event {
  private Game game;
  private Player player;

  /**
   * Creates a new player join event.
   * 
   * @param game   The game.
   * @param player The player.
   */
  public PlayerJoinEvent(Game game, Player player) {
    this.game = game;
    this.player = player;
  }

  /**
   * Gets the game.
   * 
   * @return The game.
   */
  public Game getGame() {
    return this.game;
  }

  /**
   * Gets the player.
   * 
   * @return The player.
   */
  public Player getPlayer() {
    return this.player;
  }
}
