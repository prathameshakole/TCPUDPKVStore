package server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpServer extends AbstractServer {

  private static final int BUFFER_SIZE = 1024;

  /**
   * Starts the UDP Server and listens for incoming packets.
   * @param udpSocket The DatagramSocket used for communication.
   */
  public void start(DatagramSocket udpSocket) {
    try {
      byte[] buffer = new byte[BUFFER_SIZE];

      while (true) {
        DatagramPacket requestPacket = new DatagramPacket(buffer, buffer.length);
        udpSocket.receive(requestPacket);

        handleRequest(udpSocket, requestPacket);
      }
    } catch (Exception e) {
      logMessage("ERROR: Failed to handle UDP packet.");
    }
  }

  private void handleRequest(DatagramSocket udpSocket, DatagramPacket requestPacket) throws Exception {
    InetAddress clientAddress = requestPacket.getAddress();
    int clientPort = requestPacket.getPort();

    String receivedData = new String(requestPacket.getData(), 0, requestPacket.getLength());
    logMessage("Received request: " + receivedData + " from " + clientAddress + ":" + clientPort);

    Response response = processRequest(receivedData);

    byte[] responseData = response.getBytes();
    DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length,
            clientAddress, clientPort);
    udpSocket.send(responsePacket);

    logMessage("Sent response: " + response);
  }
}
