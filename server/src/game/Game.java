package game;

import java.util.HashMap;

public class Game {
  private final String id;
  private HashMap<String, Player> players = new HashMap<>();

  public Game() {
    this.id = String.format("%04d", (int) (Math.random() * 10000));
  }

  public String getId() {
    return this.id;
  }

  public Player createPlayer() {
    Player player = new Player();
    players.put(player.getId(), player);
    return player;
  }

  public Player getPlayer(String id) {
    return players.get(id);
  }
}
