package game;

public class Player {
  private final String id;
  private String name;

  public Player() {
    this.id = String.format("%06d", (int) (Math.random() * 1000000));
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
