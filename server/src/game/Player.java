package game;

public class Player {
  private final String id;
  private String name;

  public Player(String id) {
    this.id = id;
  }

  public String getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
