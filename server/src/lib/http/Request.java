package lib.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Request {
  private RequestType type;
  private String path;
  private String protocol;
  private HashMap<String, String> headers = new HashMap<>();
  private HashMap<String, String> cookies = new HashMap<>();
  private String body;

  public Request(InputStream data) throws IOException {
    boolean isFirstLine = true;
    boolean finishReading = false;
    while (!finishReading) {
      String line = "";
      while (!line.endsWith("\r\n")) {
        line += (char) data.read();
      }
      line = line.substring(0, line.length() - 2);

      if (isFirstLine) {
        String[] lineFragments = line.split(" ");

        this.type = RequestType.fromString(lineFragments[0]);
        this.path = lineFragments[1];
        this.protocol = lineFragments[2];

        isFirstLine = false;
      } else if (line.equals("")) {
        finishReading = true;
      } else {
        int colonIndex = line.indexOf(":");
        headers.put(line.substring(0, colonIndex), line.substring(colonIndex + 2));
      }
    }

    String contentLengthStr = headers.get("Content-Length");
    if (contentLengthStr != null) {
      int contentLength = Integer.parseInt(contentLengthStr);
      byte[] bodyBytes = new byte[contentLength];
      data.read(bodyBytes, 0, contentLength);

      this.body = "";
      for (byte b : bodyBytes) {
        this.body += (char) b;
      }
    }

    if (headers.containsKey("Cookie")) {
      String cookiesString = headers.get("Cookie");

      Pattern cookiePattern = Pattern.compile("([^ ].*?)=(?:(.*?);|(.*?)$)");
      Matcher cookieMatcher = cookiePattern.matcher(cookiesString);
      while (cookieMatcher.find()) {
        if (cookieMatcher.group(2) != null) {
          cookies.put(cookieMatcher.group(1), cookieMatcher.group(2));
        } else {
          cookies.put(cookieMatcher.group(1), cookieMatcher.group(3));
        }
      }
    }
  }

  public RequestType getType() {
    return this.type;
  }

  public String getPath() {
    return this.path;
  }

  public String getProtocol() {
    return this.protocol;
  }

  public HashMap<String, String> getHeaders() {
    return this.headers;
  }

  public String getHeader(String name) {
    return this.headers.get(name);
  }

  public String getCookie(String name) {
    return this.cookies.get(name);
  }

  public String getBody() {
    return this.body;
  }
}
