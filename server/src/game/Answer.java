package game;

public class Answer {
  private Question question;
  private int answer;

  public Answer(Question question, int answer) {
    this.question = question;
    this.answer = answer;
  }

  public Question getQuestion() {
    return this.question;
  }

  public int getAnswer() {
    return this.answer;
  }
}
