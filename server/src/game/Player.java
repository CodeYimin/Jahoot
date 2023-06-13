package game;

import java.util.ArrayList;

/**
 * Represents a player in a game.
 */
public class Player {
  private final String id;
  private String name;
  private ArrayList<Answer> answers = new ArrayList<>();

  /**
   * Creates a new player.
   * 
   * @param id The player's ID.
   */
  public Player(String id) {
    this.id = id;
  }

  /**
   * Gets the player's ID.
   * 
   * @return The player's ID.
   */
  public String getId() {
    return this.id;
  }

  /**
   * Gets the player's name.
   * 
   * @return The player's name.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Sets the player's name.
   * 
   * @param name The player's name.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Adds an answer to the player
   * 
   * @param answer The answer to add.
   */
  public void addAnswer(Answer answer) {
    this.answers.add(answer);
  }

  /**
   * Gets the player's answer to a question.
   * 
   * @param question The question to get the answer for.
   * @return The player's answer to the question.
   */
  public Integer getAnswer(Question question) {
    for (Answer answer : this.answers) {
      if (answer.getQuestion() == question) {
        return answer.getAnswer();
      }
    }

    return null;
  }

  /**
   * Checks if the player's answer to a question is correct.
   * 
   * @param question The question to check.
   * @return True if the player's answer is correct, otherwise false.
   */
  public boolean getAnswerIsCorrect(Question question) {
    Integer answer = this.getAnswer(question);
    if (answer == null) {
      return false;
    }

    return question.isCorrectAnswer(answer);
  }

  /**
   * Gets the player's score.
   * 
   * @param questions The questions to check.
   * @return The player's score.
   */
  public int getScore(Question[] questions) {
    int score = 0;
    for (int i = 0; i < questions.length; i++) {
      if (this.getAnswerIsCorrect(questions[i])) {
        score += 1000;
      }
    }

    return score;
  }
}
