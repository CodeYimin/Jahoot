package game.events;

import game.Game;

public class GameStartEvent implements Event {
  private final Game game;

  public GameStartEvent(Game game) {
    this.game = game;
  }

  public Game getGame() {
    return this.game;
  }
}
