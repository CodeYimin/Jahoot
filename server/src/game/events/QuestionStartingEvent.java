package game.events;

import game.Game;
import game.Question;

public class QuestionStartingEvent implements Event {
  private Game game;
  private int timeRemaining;
  private Question question;

  public QuestionStartingEvent(Game game, Question question, int timeRemaining) {
    this.game = game;
    this.question = question;
    this.timeRemaining = timeRemaining;
  }

  public Game getGame() {
    return this.game;
  }

  public Question getQuestion() {
    return this.question;
  }

  public int getTimeRemaining() {
    return this.timeRemaining;
  }
}
