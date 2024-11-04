package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer extends AbstractServer{

  /**
   * Starts the TCP Server and listens for connections.
   * @param serverSocket The server socket.
   */
  public void start(ServerSocket serverSocket) {
    logMessage("TCP Server is running on port " + serverSocket.getLocalPort());

    try {
      while (true) {
        Socket socket = serverSocket.accept();
        logMessage("Client connected.");
        handleRequest(socket);
      }
    } catch (IOException e) {
      logMessage("ERROR: Could not accept connection.");
      e.printStackTrace();
    }
  }


  private void handleRequest(Socket socket) {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

      String request = reader.readLine();

      if (request == null || request.trim().isEmpty()) {
        logMessage("Received malformed request from " + socket.getInetAddress() + ":" + socket.getPort());
        return;
      }

      logMessage("Received request from " + socket.getInetAddress() + ":" + socket.getPort() + " : " + request);

      Response response = processRequest(request);

      writer.write(String.valueOf(response));
      writer.newLine();
      writer.flush();

      logMessage("Sent response to " + socket.getInetAddress() + ":" + socket.getPort() + " : " + response);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
