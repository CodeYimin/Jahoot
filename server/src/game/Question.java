package game;

public class Question {
  private String question;
  private String[] answers;
  private int correctAnswerIndex;
  private int duration;

  public Question(String question, String[] answers, int correctAnswerIndex, int duration) {
    this.question = question;
    this.answers = answers;
    this.correctAnswerIndex = correctAnswerIndex;
    this.duration = duration;
  }

  public String getQuestion() {
    return this.question;
  }

  public String[] getAnswers() {
    return this.answers;
  }

  public int getCorrectAnswerIndex() {
    return this.correctAnswerIndex;
  }

  public boolean isCorrectAnswer(int index) {
    return index == this.correctAnswerIndex;
  }

  public int getDuration() {
    return this.duration;
  }
}
