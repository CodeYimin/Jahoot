package lib.websocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import lib.http.Request;
import lib.http.Response;
import lib.http.ResponseStatus;

public class WebSocketUtils {

  public static void upgradeToWebsocket(OutputStream output, Request request)
      throws UnsupportedEncodingException, IOException, IllegalStateException,
      NoSuchAlgorithmException {
    String key = request.getHeader("Sec-WebSocket-Key");
    String verifyKey = key + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
    byte[] verifyKeyHash = MessageDigest.getInstance("SHA-1").digest(verifyKey.getBytes());
    String verifyKeyHashString = Base64.getEncoder().encodeToString(verifyKeyHash);

    Response response = new Response(ResponseStatus.SWITCHING_PROTOCOLS);
    response.addHeader("Connection", "Upgrade");
    response.addHeader("Upgrade", "websocket");
    response.addHeader("Sec-WebSocket-Accept", verifyKeyHashString);
    response.send(output);
  }

  public static String readWebsocketMessage(InputStream input) throws IOException {
    int a = input.read(); // Bit 1: FIN, RSV1, RSV2, RSV3, opcode
    int length = input.read() & 0b01111111;

    if (length == 126) {
      length = (input.read() << 8) + input.read();
    } else if (length == 127) {
      length = 0;
      for (int i = 0; i < 8; i++) {
        length = (length << 8) + input.read();
      }
    }

    byte[] maskKey = new byte[4];
    input.read(maskKey, 0, 4);

    byte[] encodedData = new byte[length];
    input.read(encodedData, 0, length);

    String decodedData = "";
    for (int i = 0; i < length; i++) {
      decodedData += (char) (encodedData[i] ^ maskKey[i % 4]);
    }

    return decodedData;
  }

  public static void sendWebsocketMessage(OutputStream output, String message) throws IOException {
    output.write(0b10000001);
    if (message.length() < 126) {
      output.write(message.length());
    } else if (message.length() < 65536) {
      output.write(126);
      for (int i = 1; i >= 0; i--) {
        output.write(message.length() >> (8 * i));
      }
    } else {
      output.write(127);
      for (int i = 7; i >= 0; i--) {
        output.write((int) (((long) message.length()) >> (8 * i)));
      }
    }

    output.write(message.getBytes());
  }
}
