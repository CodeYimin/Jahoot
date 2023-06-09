package test;

import java.net.Socket;

import lib.http.Request;
import lib.http.RequestHandler;

public class TestRequestHandler implements RequestHandler {
  @Override
  public void onRequest(Socket socket, Request request) {
    System.out.println(request.getBody());
    System.out.println(request.getCookie("b"));
  }
}
