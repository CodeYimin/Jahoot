package game.events;

import game.Game;
import game.Player;
import game.Question;

public class QuestionEndEvent implements Event {
  private Game game;
  private Question question;
  private int[] answerCounts;
  private Player[] leaderboard;

  public QuestionEndEvent(Game game, Question question, int[] answerCounts, Player[] leaderboard) {
    this.game = game;
    this.question = question;
    this.answerCounts = answerCounts;
    this.leaderboard = leaderboard;
  }

  public Game getGame() {
    return this.game;
  }

  public Question getQuestion() {
    return this.question;
  }

  public int[] getAnswerCounts() {
    return this.answerCounts;
  }

  public Player[] getLeaderboard() {
    return this.leaderboard;
  }
}
