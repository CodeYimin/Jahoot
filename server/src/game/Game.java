package game;

import java.util.ArrayList;

import game.events.EventManager;
import game.events.GameEndEvent;
import game.events.PlayerAnswerEvent;
import game.events.QuestionEndEvent;
import game.events.QuestionStartEvent;
import game.events.QuestionStartingEvent;

/**
 * Represents a game.
 */
public class Game {
  private final String id;
  private Operator operator;
  private ArrayList<Player> players = new ArrayList<>();
  private EventManager eventManager = new EventManager();
  private GameState state = GameState.LOBBY;
  private int currentQuestionIndex = -1;
  private Question[] questions;

  /**
   * Creates a new game.
   * 
   * @param id        The game's ID.
   * @param operator  The operator of the game.
   * @param questions The questions of the game.
   */
  public Game(String id, Operator operator, Question[] questions) {
    this.id = id;
    this.operator = operator;
    this.questions = questions;
  }

  /**
   * Gets the ID of the game.
   * 
   * @return The ID of the game.
   */
  public String getId() {
    return this.id;
  }

  /**
   * Gets the operator of the game.
   * 
   * @return The operator of the game.
   */
  public Operator getOperator() {
    return this.operator;
  }

  /**
   * Checks if the game is on the last question.
   * 
   * @return Whether the game is on the last question.
   */
  public boolean isLastQuestion() {
    return this.currentQuestionIndex == this.questions.length - 1;
  }

  /**
   * Make a player answer a question.
   * 
   * @param player The player to answer the question.
   * @param answer The answer to the question.
   */
  public void answer(Player player, Answer answer) {
    if (this.state != GameState.PLAYING) {
      return;
    }

    if (this.currentQuestionIndex < 0) {
      return;
    }

    if (this.currentQuestionIndex >= this.questions.length) {
      return;
    }

    if (this.questions[this.currentQuestionIndex] != answer.getQuestion()) {
      return;
    }

    player.addAnswer(answer);
    eventManager.emitEvent(PlayerAnswerEvent.class, new PlayerAnswerEvent(this, player, answer));
  }

  /**
   * Creates a player and adds them to the game.
   * 
   * @return The created player.
   */
  public Player createPlayer() {
    String playerId = String.format("%08d", (int) (Math.random() * 100000000));
    Player player = new Player(playerId);
    players.add(player);
    return player;
  }

  /**
   * Gets a player by their ID.
   * 
   * @param id The ID of the player.
   * @return The player with the ID, or null if no player has the ID.
   */
  public Player getPlayer(String id) {
    for (Player player : players) {
      if (player.getId().equals(id)) {
        return player;
      }
    }

    return null;
  }

  /**
   * Gets the players with ascending score order.
   * 
   * @return The players with ascending score order.
   */
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

  /**
   * Gets a question by its index.
   * 
   * @param index The index of the question.
   * @return The question at the index, or null if the index is out of bounds.
   */
  public Question getQuestion(int index) {
    if ((0 > index) || (index >= questions.length)) {
      return null;
    }
    return questions[index];
  }

  /**
   * Get the amount of answers for each answer of a question.
   * 
   * @param question The question to get the answer counts for.
   * @return The amount of answers for each answer of a question.
   */
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

  /**
   * Gets the event manager of the game.
   * 
   * @return The event manager of the game.
   */
  public EventManager getEventManager() {
    return this.eventManager;
  }

  /**
   * Gets the questions of the game.
   * 
   * @return The questions of the game.
   */
  public Question[] getQuestions() {
    return questions;
  }

  /**
   * Start the next question of the game.
   */
  public void startNextQuestion() {
    if (state == GameState.PLAYING) {
      return;
    }

    if (isLastQuestion()) {
      state = GameState.FINISHED;
      eventManager.emitEvent(GameEndEvent.class, new GameEndEvent(this));
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

  /**
   * Ends the current question of the game.
   */
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

  /**
   * Gets the current question of the game.
   * 
   * @return The current question of the game.
   */
  public Question getCurrentQuestion() {
    if ((0 > currentQuestionIndex) || (currentQuestionIndex >= questions.length)) {
      return null;
    }

    return questions[currentQuestionIndex];
  }

  /**
   * Gets the players of the game.
   * 
   * @return The players of the game.
   */
  public ArrayList<Player> getPlayers() {
    return players;
  }
}
