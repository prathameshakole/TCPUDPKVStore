package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class TcpClient extends AbstractClient {

  /**
   * Sends a single request to the server and handles the response.
   *
   * @param hostname The server's IP address.
   * @param port The server's port number.
   * @param command The command to send to the server.
   */
  @Override
  protected void sendRequest(String hostname, int port, String command) {
    try (Socket socket = new Socket(hostname, port)) {
      socket.setSoTimeout(TIMEOUT);

      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
      BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

      writer.write(command);
      writer.newLine();
      writer.flush();
      logMessage("Sent request: " + command);

      String response = reader.readLine();
      if (response != null) {
        logMessage("Server Response: " + response);
      } else {
        logMessage("No response from server.");
      }

      reader.close();
      writer.close();
    } catch (SocketTimeoutException e) {
      logMessage("ERROR: Server response timeout for request: " + command);
    } catch (Exception e) {
      logMessage("ERROR: Failed to send request: " + command);
    }
  }





}
