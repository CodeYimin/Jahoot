package game;

/**
 * Represents a question in the game.
 */
public class Question {
  private String question;
  private String[] answers;
  private int correctAnswerIndex;
  private int duration;

  /**
   * Creates a new question.
   * 
   * @param question           The question.
   * @param answers            The answers.
   * @param correctAnswerIndex The index of the correct answer.
   * @param duration           The duration of the question in seconds.
   */
  public Question(String question, String[] answers, int correctAnswerIndex, int duration) {
    this.question = question;
    this.answers = answers;
    this.correctAnswerIndex = correctAnswerIndex;
    this.duration = duration;
  }

  /**
   * Gets the question.
   * 
   * @return The question.
   */
  public String getQuestion() {
    return this.question;
  }

  /**
   * Gets the answers.
   * 
   * @return The answers.
   */
  public String[] getAnswers() {
    return this.answers;
  }

  /**
   * Gets the correct answer index.
   * 
   * @return The correct answer index.
   */
  public int getCorrectAnswerIndex() {
    return this.correctAnswerIndex;
  }

  /**
   * Checks if the given index is the correct answer.
   * 
   * @param index The index to check.
   * @return True if the index is the correct answer, otherwise false.
   */
  public boolean isCorrectAnswer(int index) {
    return index == this.correctAnswerIndex;
  }

  /**
   * Gets the duration of the question in seconds.
   * 
   * @return The duration of the question in seconds.
   */
  public int getDuration() {
    return this.duration;
  }
}
