package game;

import java.util.ArrayList;

public class Player {
  private final String id;
  private String name;
  private ArrayList<Answer> answers = new ArrayList<>();

  public Player(String id) {
    this.id = id;
  }

  public String getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void addAnswer(Answer answer) {
    this.answers.add(answer);
  }

  public Integer getAnswer(Question question) {
    for (Answer answer : this.answers) {
      if (answer.getQuestion() == question) {
        return answer.getAnswer();
      }
    }

    return null;
  }

  public boolean getAnswerIsCorrect(Question question) {
    Integer answer = this.getAnswer(question);
    if (answer == null) {
      return false;
    }

    return question.isCorrectAnswer(answer);
  }

  public int getScore(Question[] questions) {
    int score = 0;
    for (int i = 0; i < questions.length; i++) {
      if (this.getAnswerIsCorrect(questions[i])) {
        score++;
      }
    }

    return score;
  }
}
