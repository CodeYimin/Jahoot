package lib.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * A response that sends a file.
 */
public class FileResponse extends Response {
  private File file;

  /**
   * Creates a new FileResponse.
   * 
   * @param status The status of the response.
   * @param file   The file to send.
   * @throws IOException
   */
  public FileResponse(ResponseStatus status, File file) throws IOException {
    super(status);
    this.file = file;

    String fileExtension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
    String contentType;
    if (fileExtension.equals("html")) {
      contentType = "text/html";
    } else if (fileExtension.equals("js")) {
      contentType = "text/javascript";
    } else if (fileExtension.equals("css")) {
      contentType = "text/css";
    } else if (fileExtension.equals("png")) {
      contentType = "image/png";
    } else if (fileExtension.equals("jpg")) {
      contentType = "image/jpeg";
    } else if (fileExtension.equals("gif")) {
      contentType = "image/gif";
    } else {
      contentType = "application/octet-stream";
    }

    addHeader("Content-Length", String.valueOf(file.length()));
    addHeader("Content-Type", contentType);
  }

  /**
   * Gets the file that is being sent.
   * 
   * @return The file that is being sent.
   */
  public File getFile() {
    return this.file;
  }

  @Override
  public String getBody() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setBody(String body) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void send(OutputStream output) throws IOException {
    super.send(output);

    FileInputStream fileStream = new FileInputStream(file);
    byte[] fileBytes = new byte[(int) file.length()];
    fileStream.read(fileBytes, 0, fileBytes.length);
    fileStream.close();

    output.write(fileBytes, 0, fileBytes.length);
  }
}
