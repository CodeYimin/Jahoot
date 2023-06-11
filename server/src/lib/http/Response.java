package lib.http;

import java.io.IOException;
import java.io.OutputStream;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

public class Response {
  private String protocol;
  private ResponseStatus status;
  private ArrayList<SimpleEntry<String, String>> headers;
  private String body;

  public Response(ResponseStatus status) {
    this.protocol = "HTTP/1.1";
    this.status = status;
    this.headers = new ArrayList<>();
  }

  public ResponseStatus getStatus() {
    return this.status;
  }

  public String getProtocol() {
    return this.protocol;
  }

  public ArrayList<SimpleEntry<String, String>> getHeaders() {
    return this.headers;
  }

  public void addHeader(String key, String value) {
    this.headers.add(new SimpleEntry<>(key, value));
  }

  public String getBody() {
    return this.body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public void send(OutputStream output) throws IOException {
    byte[] bytes = this.toString().getBytes();
    output.write(bytes, 0, bytes.length);
  }

  @Override
  public String toString() {
    String string = "";

    string += protocol;
    string += " ";

    string += status.toString();
    string += "\r\n";

    for (SimpleEntry<String, String> header : headers) {
      string += header.getKey();
      string += ": ";
      string += header.getValue();
      string += "\r\n";
    }

    string += "\r\n";

    if (body != null) {
      string += body;
    }

    return string;
  }
}
