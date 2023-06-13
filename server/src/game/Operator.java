package game;

/**
 * Represents an operator in the game.
 */
public class Operator {
  private final String id;

  /**
   * Creates a new operator.
   * 
   * @param id The operator's ID.
   */
  public Operator(String id) {
    this.id = id;
  }

  /**
   * Gets the operator's ID.
   * 
   * @return The operator's ID.
   */
  public String getId() {
    return this.id;
  }
}
