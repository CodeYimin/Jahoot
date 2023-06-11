package game.events;

import game.Game;
import game.GameManager;

public class GameCreateEvent implements Event {
  private GameManager gameManager;
  private Game game;

  public GameCreateEvent(GameManager gameManager, Game game) {
    this.gameManager = gameManager;
    this.game = game;
  }

  public GameManager getGameManager() {
    return this.gameManager;
  }

  public Game getGame() {
    return this.game;
  }
}
