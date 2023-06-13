package game.events;

import game.Answer;
import game.Game;
import game.Player;

/**
 * Represents an event where a player answers a question.
 */
public class PlayerAnswerEvent implements Event {
  private Game game;
  private Player player;
  private Answer answer;

  /**
   * Creates a new player answer event.
   * 
   * @param game   The game.
   * @param player The player.
   * @param answer The answer.
   */
  public PlayerAnswerEvent(Game game, Player player, Answer answer) {
    this.game = game;
    this.player = player;
    this.answer = answer;
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

  /**
   * Gets the answer.
   * 
   * @return The answer.
   */
  public Answer getAnswer() {
    return this.answer;
  }
}
