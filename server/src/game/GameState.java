package game;

/**
 * Represents the state of a game.
 */
public enum GameState {
  LOBBY, WAITING, PLAYING, FINISHED;

  /**
   * Converts a string to a game state.
   * 
   * @param string The string to convert.
   * @return The game state.
   */
  public static GameState fromString(String string) {
    if (string.equals("LOBBY")) {
      return LOBBY;
    } else if (string.equals("WAITING")) {
      return WAITING;
    } else if (string.equals("PLAYING")) {
      return PLAYING;
    } else if (string.equals("FINISHED")) {
      return FINISHED;
    } else {
      return null;
    }
  }

  /**
   * Converts a game state to a string.
   * 
   * @return The string.
   */
  public String toString() {
    if (this == LOBBY) {
      return "LOBBY";
    } else if (this == WAITING) {
      return "WAITING";
    } else if (this == PLAYING) {
      return "PLAYING";
    } else if (this == FINISHED) {
      return "FINISHED";
    } else {
      return null;
    }
  }
}
