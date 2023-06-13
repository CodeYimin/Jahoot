package game;

/**
 * Represents a player's answer to a question.
 */
public class Answer {
  private Question question;
  private int answer;

  /**
   * Creates a new answer.
   * 
   * @param question The question.
   * @param answer   The answer.
   */
  public Answer(Question question, int answer) {
    this.question = question;
    this.answer = answer;
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
   * Gets the answer.
   * 
   * @return The answer.
   */
  public int getAnswer() {
    return this.answer;
  }
}
