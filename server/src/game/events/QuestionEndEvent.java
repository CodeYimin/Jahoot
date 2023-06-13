package game.events;

import game.Game;
import game.Player;
import game.Question;

/**
 * Represents an event where a question ends.
 */
public class QuestionEndEvent implements Event {
  private Game game;
  private Question question;
  private int[] answerCounts;
  private Player[] leaderboard;

  /**
   * Creates a new question end event.
   * 
   * @param game         The game.
   * @param question     The question.
   * @param answerCounts The answer counts.
   * @param leaderboard  The leaderboard.
   */
  public QuestionEndEvent(Game game, Question question, int[] answerCounts, Player[] leaderboard) {
    this.game = game;
    this.question = question;
    this.answerCounts = answerCounts;
    this.leaderboard = leaderboard;
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
   * Gets the question.
   * 
   * @return The question.
   */
  public Question getQuestion() {
    return this.question;
  }

  /**
   * Gets the answer counts.
   * 
   * @return The answer counts.
   */
  public int[] getAnswerCounts() {
    return this.answerCounts;
  }

  /**
   * Gets the leaderboard.
   * 
   * @return The leaderboard.
   */
  public Player[] getLeaderboard() {
    return this.leaderboard;
  }
}
