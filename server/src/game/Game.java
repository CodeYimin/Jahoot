package game;

import java.util.ArrayList;

import game.events.EventManager;
import game.events.QuestionEndEvent;
import game.events.QuestionStartEvent;
import game.events.QuestionStartingEvent;

public class Game {
  private final String id;
  private Operator operator;
  private ArrayList<Player> players = new ArrayList<>();
  private EventManager eventManager = new EventManager();
  private GameState state = GameState.LOBBY;
  private int currentQuestionIndex = -1;
  private Question[] questions;

  public Game(String id, Operator operator, Question[] questions) {
    this.id = id;
    this.operator = operator;
    this.questions = questions;
  }

  public String getId() {
    return this.id;
  }

  public Operator getOperator() {
    return this.operator;
  }

  public Player createPlayer() {
    String playerId = String.format("%08d", (int) (Math.random() * 100000000));
    Player player = new Player(playerId);
    players.add(player);
    return player;
  }

  public Player getPlayer(String id) {
    for (Player player : players) {
      if (player.getId().equals(id)) {
        return player;
      }
    }

    return null;
  }

  public Player[] getPlayersAscendingScore() {
    Player[] players = this.players.toArray(new Player[this.players.size()]);
    for (int i = 0; i < players.length; i++) {
      for (int j = i + 1; j < players.length; j++) {
        if (players[i].getScore(questions) < players[j].getScore(questions)) {
          Player temp = players[i];
          players[i] = players[j];
          players[j] = temp;
        }
      }
    }
    return players;
  }

  public Question getQuestion(int index) {
    if ((0 > index) || (index >= questions.length)) {
      return null;
    }
    return questions[index];
  }

  public int[] getAnswerCounts(Question question) {
    int[] counts = new int[question.getAnswers().length];
    for (Player player : players) {
      Integer answer = player.getAnswer(question);
      if (answer != null) {
        counts[answer]++;
      }
    }
    return counts;
  }

  public EventManager getEventManager() {
    return this.eventManager;
  }

  public Question[] getQuestions() {
    return questions;
  }

  public void startNextQuestion() {
    if (state == GameState.PLAYING) {
      return;
    }

    state = GameState.PLAYING;
    currentQuestionIndex++;
    Question question = getCurrentQuestion();

    new Thread(new Runnable() {
      @Override
      public void run() {
        for (int i = 5000; i > 0; i -= 1000) {
          eventManager.emitEvent(QuestionStartingEvent.class, new QuestionStartingEvent(Game.this, question, i));
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        state = GameState.PLAYING;
        eventManager.emitEvent(QuestionStartEvent.class, new QuestionStartEvent(Game.this, question));
        try {
          Thread.sleep(question.getDuration());
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        if (getCurrentQuestion() == question) {
          endQuestion();
        }
      }
    }).start();
  }

  public void endQuestion() {
    if (state != GameState.PLAYING) {
      return;
    }
    state = GameState.WAITING;
    eventManager.emitEvent(
        QuestionEndEvent.class,
        new QuestionEndEvent(this, getCurrentQuestion(),
            getAnswerCounts(getCurrentQuestion()),
            getPlayersAscendingScore()));
  }

  public Question getCurrentQuestion() {
    if ((0 > currentQuestionIndex) || (currentQuestionIndex >= questions.length)) {
      return null;
    }

    return questions[currentQuestionIndex];
  }

  public ArrayList<Player> getPlayers() {
    return players;
  }
}
