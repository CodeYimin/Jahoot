package game;

import java.util.Collection;
import java.util.HashMap;

import game.events.EventManager;
import game.events.GameStartEvent;

public class Game {
  private final String id;
  private boolean started = false;
  private HashMap<String, Player> players = new HashMap<>();
  private EventManager eventManager = new EventManager();

  public Game() {
    // this.id = String.format("%04d", (int) (Math.random() * 10000));
    // todo remove
    this.id = "1111";
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
    if (started) {
      return false;
    }
    started = true;
    eventManager.emitEvent(GameStartEvent.class, new GameStartEvent(this));
    return true;
  }

  public Collection<Player> getPlayers() {
    return players.values();
  }
}
