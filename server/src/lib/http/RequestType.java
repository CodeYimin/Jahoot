package lib.http;

/**
 * The type of an HTTP request.
 */
public enum RequestType {
  GET, POST;

  /**
   * Converts a string to a RequestType.
   * 
   * @param string The string to convert.
   * @return The RequestType.
   */
  public static RequestType fromString(String string) {
    if (string.equals("GET")) {
      return GET;
    } else if (string.equals("POST")) {
      return POST;
    } else {
      return null;
    }
  }

  /**
   * Converts a RequestType to a string.
   * 
   * @return The string.
   */
  public String toString() {
    if (this == GET) {
      return "GET";
    } else if (this == POST) {
      return "POST";
    } else {
      return null;
    }
  }
}
