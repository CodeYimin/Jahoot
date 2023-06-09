package lib.http;

public enum RequestType {
  GET, POST;

  public static RequestType fromString(String string) {
    if (string.equals("GET")) {
      return GET;
    } else if (string.equals("POST")) {
      return POST;
    } else {
      return null;
    }
  }

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
