package game;

import java.util.Collection;
import java.util.HashMap;

import game.events.EventManager;
import game.events.GameStartEvent;
import game.events.QuestionEndEvent;
import game.events.QuestionStartEvent;
import game.events.QuestionStartingEvent;

public class Game {
  private final String id;
  private HashMap<String, Player> players = new HashMap<>();
  private EventManager eventManager = new EventManager();
  private GameState state = GameState.LOBBY;
  private int currentQuestionIndex = -1;
  private Question[] questions;

  public Game(String id, Question[] questions) {
    this.id = id;
    this.questions = questions;
  }

  public String getId() {
    return this.id;
  }

  public Player createPlayer() {
    String playerId = String.format("%08d", (int) (Math.random() * 100000000));
    Player player = new Player(playerId);
    players.put(playerId, player);
    return player;
  }

  public Player getPlayer(String id) {
    return players.get(id);
  }

  public EventManager getEventManager() {
    return this.eventManager;
  }

  public boolean start() {
    if (state != GameState.LOBBY) {
      return false;
    }
    startNextQuestion();
    eventManager.emitEvent(GameStartEvent.class, new GameStartEvent(this));
    return true;
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
      }
    }).start();
  }

  public void endQuestion() {
    if (state != GameState.PLAYING) {
      return;
    }
    state = GameState.WAITING;
    eventManager.emitEvent(QuestionEndEvent.class, new QuestionEndEvent(this, getCurrentQuestion()));
  }

  public Question getCurrentQuestion() {
    if ((0 > currentQuestionIndex) || (currentQuestionIndex >= questions.length)) {
      return null;
    }

    return questions[currentQuestionIndex];
  }

  public Collection<Player> getPlayers() {
    return players.values();
  }
}
