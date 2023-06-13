package lib.http;

import java.io.IOException;
import java.io.OutputStream;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

/**
 * An HTTP response.
 */
public class Response {
  private String protocol;
  private ResponseStatus status;
  private ArrayList<SimpleEntry<String, String>> headers;
  private String body;

  /**
   * Creates a new Response.
   * 
   * @param status The status of the response.
   */
  public Response(ResponseStatus status) {
    this.protocol = "HTTP/1.1";
    this.status = status;
    this.headers = new ArrayList<>();
  }

  /**
   * Gets the status of the response.
   * 
   * @return The status of the response.
   */
  public ResponseStatus getStatus() {
    return this.status;
  }

  /**
   * Gets the protocol of the response.
   * 
   * @return The protocol of the response.
   */
  public String getProtocol() {
    return this.protocol;
  }

  /**
   * Gets the headers of the response.
   * 
   * @return The headers of the response.
   */
  public ArrayList<SimpleEntry<String, String>> getHeaders() {
    return this.headers;
  }

  /**
   * Adds a header to the response.
   * 
   * @param key   The key of the header.
   * @param value The value of the header.
   */
  public void addHeader(String key, String value) {
    this.headers.add(new SimpleEntry<>(key, value));
  }

  /**
   * Gets the body of the response.
   * 
   * @return The body of the response.
   */
  public String getBody() {
    return this.body;
  }

  /**
   * Sets the body of the response.
   * 
   * @param body The body of the response.
   */
  public void setBody(String body) {
    this.body = body;
  }

  /**
   * Sends the response.
   * 
   * @param output The output stream to send the response to.
   * @throws IOException
   */
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
