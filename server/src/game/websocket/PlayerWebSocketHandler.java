package game.websocket;

import java.net.Socket;
import java.util.HashMap;

import game.Answer;
import game.Game;
import game.GameManager;
import game.Player;
import game.Question;
import game.events.EventListener;
import game.events.GameCreateEvent;
import game.events.GameEndEvent;
import game.events.PlayerJoinEvent;
import game.events.QuestionEndEvent;
import game.events.QuestionStartEvent;
import game.events.QuestionStartingEvent;
import lib.http.Request;
import lib.websocket.WebSocketHandler;
import lib.websocket.WebSocketUtils;

/**
 * Handles WebSocket connections from players.
 */
public class PlayerWebSocketHandler implements WebSocketHandler {
  private GameManager gameManager;
  private HashMap<Socket, Player> players = new HashMap<>();
  private HashMap<Player, Socket> sockets = new HashMap<>();

  /**
   * Creates a new PlayerWebSocketHandler.
   * 
   * @param gameManager The GameManager to use.
   */
  public PlayerWebSocketHandler(GameManager gameManager) {
    this.gameManager = gameManager;
    gameManager.getEventManager().addListener(GameCreateEvent.class, gameCreateEventListener);
  }

  @Override
  public boolean authorizeConnection(Socket socket, Request request) {
    String playerId = request.getCookie("playerId");
    if (playerId == null) {
      return false;
    }

    Player player = gameManager.getPlayer(playerId);
    if (player == null) {
      return false;
    }

    players.put(socket, player);
    sockets.put(player, socket);
    return true;
  }

  @Override
  public void onConnect(Socket socket) {
    Game game = gameManager.getGame(players.get(socket));
    Player player = players.get(socket);
    game.getEventManager().emitEvent(PlayerJoinEvent.class, new PlayerJoinEvent(game, player));
  }

  @Override
  public void onMessage(Socket socket, String message) {
    Player player = players.get(socket);
    Game game = gameManager.getGame(player);
    game.answer(player, new Answer(game.getCurrentQuestion(), Integer.parseInt(message)));
  }

  @Override
  public void onClose(Socket socket) {
    sockets.remove(players.get(socket));
    players.remove(socket);
  }

  private EventListener<GameCreateEvent> gameCreateEventListener = new EventListener<GameCreateEvent>() {
    @Override
    public void onEvent(GameCreateEvent event) {
      Game game = event.getGame();
      game.getEventManager().addListener(QuestionStartingEvent.class, questionStartingListener);
      game.getEventManager().addListener(QuestionStartEvent.class, questionStartListener);
      game.getEventManager().addListener(QuestionEndEvent.class, questionEndListener);
      game.getEventManager().addListener(GameEndEvent.class, gameEndListener);
    }
  };

  private EventListener<QuestionStartingEvent> questionStartingListener = new EventListener<QuestionStartingEvent>() {
    @Override
    public void onEvent(QuestionStartingEvent event) {
      Game game = event.getGame();
      int timeRemaining = event.getTimeRemaining();

      for (Player player : game.getPlayers()) {
        Socket socket = sockets.get(player);
        String jsonMessage = "{\"event\": \"questionStarting\", \"timeRemaining\": " + timeRemaining + "}";
        try {
          WebSocketUtils.sendWebsocketMessage(socket.getOutputStream(), jsonMessage);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  };

  private EventListener<QuestionStartEvent> questionStartListener = new EventListener<QuestionStartEvent>() {
    @Override
    public void onEvent(QuestionStartEvent event) {
      Game game = event.getGame();
      Question question = event.getQuestion();
      for (Player player : game.getPlayers()) {
        Socket socket = sockets.get(player);

        String jsonMessage = "{\"event\": \"questionStart\", \"answerCount\": " + question.getAnswers().length + "}";

        try {
          WebSocketUtils.sendWebsocketMessage(socket.getOutputStream(), jsonMessage);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  };

  private EventListener<QuestionEndEvent> questionEndListener = new EventListener<QuestionEndEvent>() {
    @Override
    public void onEvent(QuestionEndEvent event) {
      Game game = event.getGame();
      Question question = event.getQuestion();

      for (Player player : game.getPlayers()) {
        Socket socket = sockets.get(player);
        if (socket == null) {
          continue;
        }

        boolean questionCorrect = player.getAnswerIsCorrect(question);
        int score = questionCorrect ? 1000 : 0;

        String jsonMessage = "{\"event\": \"questionEnd\"";
        jsonMessage += ", \"correct\": " + questionCorrect;
        jsonMessage += ", \"questionScore\": " + score;
        jsonMessage += ", \"totalScore\": " + player.getScore(game.getQuestions());
        jsonMessage += "}";

        try {
          WebSocketUtils.sendWebsocketMessage(socket.getOutputStream(), jsonMessage);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  };

  private EventListener<GameEndEvent> gameEndListener = new EventListener<GameEndEvent>() {
    @Override
    public void onEvent(GameEndEvent event) {
      Game game = event.getGame();
      Player[] leaderboard = game.getPlayersAscendingScore();
      for (Player player : game.getPlayers()) {
        Socket socket = sockets.get(player);
        if (socket == null) {
          continue;
        }

        int playerPlacement = 0;
        for (int i = 0; i < leaderboard.length; i++) {
          if (leaderboard[i] == player) {
            playerPlacement = i + 1;
            break;
          }
        }

        String jsonMessage = "{\"event\": \"gameEnd\","
            + "\"placement\": " + playerPlacement + "}";

        try {
          WebSocketUtils.sendWebsocketMessage(socket.getOutputStream(), jsonMessage);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  };
}
