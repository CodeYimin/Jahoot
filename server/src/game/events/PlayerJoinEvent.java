package game.events;

import game.Game;
import game.Player;

public class PlayerJoinEvent implements Event {
  private Game game;
  private Player player;

  public PlayerJoinEvent(Game game, Player player) {
    this.game = game;
    this.player = player;
  }

  public Game getGame() {
    return this.game;
  }

  public Player getPlayer() {
    return this.player;
  }
}
