package client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class UdpClient extends AbstractClient{

  private static final int BUFFER_SIZE = 1024;

  /**
   * Sends a single request to the server and handles the response.
   *
   * @param hostname The server's IP address.
   * @param port The server's port number.
   * @param command The command to send to the server.
   */
  @Override
  protected void sendRequest(String hostname, int port, String command) {
    try (DatagramSocket socket = new DatagramSocket()) {
      socket.setSoTimeout(TIMEOUT);

      InetAddress serverAddress = InetAddress.getByName(hostname);
      byte[] requestData = command.getBytes();
      DatagramPacket requestPacket = new DatagramPacket(requestData, requestData.length,
              serverAddress, port);
      socket.send(requestPacket);
      logMessage("Sent request: " + command);
      byte[] buffer = new byte[BUFFER_SIZE];
      DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);
      socket.receive(responsePacket);

      String response = new String(responsePacket.getData(), 0, responsePacket.getLength());
      logMessage("Server Response: " + response);
    } catch (SocketTimeoutException e) {
      logMessage("ERROR: Server response timeout for request: " + command);
    } catch (Exception e) {
      logMessage("ERROR: Failed to send request: " + command);
    }
  }
}
