package game.events;

import game.Game;
import game.Question;

public class QuestionStartEvent implements Event {
  private Game game;
  private Question question;

  public QuestionStartEvent(Game game, Question question) {
    this.game = game;
    this.question = question;
  }

  public Game getGame() {
    return this.game;
  }

  public Question getQuestion() {
    return this.question;
  }
}
