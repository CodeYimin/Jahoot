package game.events;

import game.Game;
import game.Question;

/**
 * Represents an event where a question has started.
 */
public class QuestionStartEvent implements Event {
  private Game game;
  private Question question;

  /**
   * Creates a new question start event.
   * 
   * @param game     The game.
   * @param question The question.
   */
  public QuestionStartEvent(Game game, Question question) {
    this.game = game;
    this.question = question;
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
}
