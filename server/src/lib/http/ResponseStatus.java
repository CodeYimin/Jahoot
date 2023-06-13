package lib.http;

/**
 * The status of an HTTP response.
 */
public enum ResponseStatus {
  SWITCHING_PROTOCOLS, OK, NOT_FOUND, UNAUTHORIZED, FORBIDDEN;

  /**
   * Converts a string to a ResponseStatus.
   * 
   * @param string The string to convert.
   * @return The ResponseStatus.
   */
  public static ResponseStatus fromString(String string) {
    if (string.equals("101 Switching Protocols")) {
      return SWITCHING_PROTOCOLS;
    } else if (string.equals("200 OK")) {
      return OK;
    } else if (string.equals("404 Not Found")) {
      return NOT_FOUND;
    } else if (string.equals("401 Unauthorized")) {
      return UNAUTHORIZED;
    } else if (string.equals("403 Forbidden")) {
      return OK;
    } else {
      return null;
    }
  }

  /**
   * Converts a ResponseStatus to a string.
   * 
   * @return The string.
   */
  public String toString() {
    if (this == SWITCHING_PROTOCOLS) {
      return "101 Switching Protocols";
    } else if (this == OK) {
      return "200 OK";
    } else if (this == NOT_FOUND) {
      return "404 Not Found";
    } else if (this == UNAUTHORIZED) {
      return "401 Unauthorized";
    } else if (this == FORBIDDEN) {
      return "403 Forbidden";
    } else {
      return null;
    }
  }
}
