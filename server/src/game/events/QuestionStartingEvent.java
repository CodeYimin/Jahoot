package game.events;

import game.Game;
import game.Question;

/**
 * Represents an event where a question is starting.
 */
public class QuestionStartingEvent implements Event {
  private Game game;
  private int timeRemaining;
  private Question question;

  /**
   * Creates a new question starting event.
   * 
   * @param game          The game.
   * @param question      The question.
   * @param timeRemaining The time remaining.
   */
  public QuestionStartingEvent(Game game, Question question, int timeRemaining) {
    this.game = game;
    this.question = question;
    this.timeRemaining = timeRemaining;
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
   * Gets the time remaining.
   * 
   * @return The time remaining.
   */
  public int getTimeRemaining() {
    return this.timeRemaining;
  }
}
