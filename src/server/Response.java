package server;

import java.nio.charset.StandardCharsets;

public class Response {
  private String status;
  private String message;

  public static final String SUCCESS = "SUCCESS";
  public static final String ERROR = "ERROR";

  public Response(String status, String message) {
    this.status = status;
    this.message = message;
  }

  public String getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public String toString() {
    return status + ": " + message;
  }

  public static Response success(String message) {
    return new Response(SUCCESS, message);
  }

  public static Response error(String message) {
    return new Response(ERROR, message);
  }

  public byte[] getBytes() {
    return toString().getBytes(StandardCharsets.UTF_8);
  }
}