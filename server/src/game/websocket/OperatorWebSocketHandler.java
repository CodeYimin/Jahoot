package game.websocket;

import java.net.Socket;
import java.util.HashMap;

import game.Game;
import game.GameManager;
import game.Operator;
import game.Player;
import game.Question;
import game.events.EventListener;
import game.events.GameCreateEvent;
import game.events.PlayerJoinEvent;
import game.events.QuestionStartEvent;
import game.events.QuestionStartingEvent;
import lib.http.Request;
import lib.websocket.WebSocketHandler;
import lib.websocket.WebSocketUtils;

public class OperatorWebSocketHandler implements WebSocketHandler {
  private GameManager gameManager;
  private HashMap<Socket, Operator> operators = new HashMap<>();
  private HashMap<Operator, Socket> sockets = new HashMap<>();

  public OperatorWebSocketHandler(GameManager gameManager) {
    this.gameManager = gameManager;
    gameManager.getEventManager().addListener(GameCreateEvent.class, gameCreateEventListener);
  }

  @Override
  public boolean authorizeConnection(Socket socket, Request request) {
    String operatorId = request.getCookie("operatorId");
    if (operatorId == null) {
      return false;
    }

    Operator operator = gameManager.getOperator(operatorId);
    if (operator == null) {
      return false;
    }

    operators.put(socket, operator);
    sockets.put(operator, socket);
    return true;
  }

  @Override
  public void onConnect(Socket socket) {

  }

  @Override
  public void onMessage(Socket socket, String message) {
    Operator operator = operators.get(socket);
    Game game = gameManager.getGame(operator);

    if (message.equals("startNextQuestion")) {
      game.startNextQuestion();
    }
  }

  @Override
  public void onClose(Socket socket) {
    sockets.remove(operators.get(socket));
    operators.remove(socket);
  }

  private EventListener<GameCreateEvent> gameCreateEventListener = new EventListener<GameCreateEvent>() {
    @Override
    public void onEvent(GameCreateEvent event) {
      Game game = event.getGame();
      game.getEventManager().addListener(PlayerJoinEvent.class, playerJoinListener);
      game.getEventManager().addListener(QuestionStartingEvent.class, questionStartingEventListener);
      game.getEventManager().addListener(QuestionStartEvent.class, questionStartEventListener);
    }
  };

  private EventListener<PlayerJoinEvent> playerJoinListener = new EventListener<PlayerJoinEvent>() {
    @Override
    public void onEvent(PlayerJoinEvent event) {
      Game game = event.getGame();
      Player player = event.getPlayer();
      Socket operatorSocket = sockets.get(game.getOperator());

      String jsonMessage = "{\"event\": \"playerJoin\", \"name\": \"" + player.getName() + "\"}";

      try {
        WebSocketUtils.sendWebsocketMessage(operatorSocket.getOutputStream(), jsonMessage);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  };

  private EventListener<QuestionStartingEvent> questionStartingEventListener = new EventListener<QuestionStartingEvent>() {
    @Override
    public void onEvent(QuestionStartingEvent event) {
      Game game = event.getGame();
      int timeRemaining = event.getTimeRemaining();
      Socket socket = sockets.get(game.getOperator());
      Question question = event.getQuestion();
      String[] answers = question.getAnswers();

      String jsonMessage = "{\"event\": \"questionStarting\","
          + "\"timeRemaining\": " + timeRemaining + ","
          + "\"question\": {\"question\": \"" + question.getQuestion() + "\", \"answers\": [";

      for (int i = 0; i < answers.length; i++) {
        jsonMessage += "\"" + answers[i] + "\"";
        if (i < answers.length - 1) {
          jsonMessage += ",";
        }
      }

      jsonMessage += "]}}";

      try {
        WebSocketUtils.sendWebsocketMessage(socket.getOutputStream(), jsonMessage);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  };

  private EventListener<QuestionStartEvent> questionStartEventListener = new EventListener<QuestionStartEvent>() {
    @Override
    public void onEvent(QuestionStartEvent event) {
      Game game = event.getGame();
      Socket socket = sockets.get(game.getOperator());
      Question question = event.getQuestion();
      String[] answers = question.getAnswers();

      String jsonMessage = "{\"event\": \"questionStart\","
          + "\"question\": {\"question\": \"" + question.getQuestion() + "\", \"answers\": [";

      for (int i = 0; i < answers.length; i++) {
        jsonMessage += "\"" + answers[i] + "\"";
        if (i < answers.length - 1) {
          jsonMessage += ",";
        }
      }

      jsonMessage += "]}}";

      try {
        WebSocketUtils.sendWebsocketMessage(socket.getOutputStream(), jsonMessage);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  };
}
