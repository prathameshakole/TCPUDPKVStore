package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public abstract class AbstractClient {
  protected static final int TIMEOUT = 3000;
  protected static final List<String> PRE_POPULATION_DATA = Arrays.asList(
          "put prathamesh akole",
          "put aditya shinde",
          "put ishan sarode",
          "put dnyanesh patil",
          "put kevin marino"
  );

  protected abstract void sendRequest(String hostname, int port, String command);

  public void start(String hostname, int port, String protocol) throws Exception {
    if (!isServerAvailable(hostname, port, protocol)) {
      logMessage("ERROR : " + protocol + " server is not reachable. Please ensure the server is running.");
      return;
    }

    prePopulateStore(hostname, port);
    performAutomaticOperations(hostname, port);
    acceptUserCommands(hostname, port);
  }

  protected boolean isServerAvailable(String hostname, int port, String protocol) {
    if (protocol.equalsIgnoreCase("TCP")) {
      return isTcpServerAvailable(hostname, port);
    } else if (protocol.equalsIgnoreCase("UDP")) {
      return isUdpServerAvailable(hostname, port);
    } else {
      logMessage("ERROR : Unknown protocol: " + protocol);
      return false;
    }
  }

  private boolean isTcpServerAvailable(String hostname, int port) {
    try (Socket socket = new Socket()) {
      socket.connect(new InetSocketAddress(hostname, port), TIMEOUT); // Attempt to connect to the server
      return true;
    } catch (SocketTimeoutException e) {
      logMessage("ERROR : TCP connection to server timed out.");
    } catch (IOException e) {
      logMessage("ERROR : TCP server is unreachable: " + e.getMessage());
    }
    return false;
  }


  private boolean isUdpServerAvailable(String hostname, int port) {
    try (DatagramSocket udpSocket = new DatagramSocket()) {
      udpSocket.setSoTimeout(TIMEOUT);
      InetAddress serverAddress = InetAddress.getByName(hostname);

      String testMessage = "ping";
      byte[] buffer = testMessage.getBytes();
      DatagramPacket packet = new DatagramPacket(buffer, buffer.length, serverAddress, port);
      udpSocket.send(packet);

      DatagramPacket response = new DatagramPacket(new byte[buffer.length], buffer.length);
      udpSocket.receive(response);

      logMessage("UDP server is reachable.");
      return true;
    } catch (SocketTimeoutException e) {
      logMessage("ERROR : UDP server response timed out.");
    } catch (IOException e) {
      logMessage("ERROR : UDP server is unreachable: " + e.getMessage());
    }
    return false;
  }

  protected void prePopulateStore(String hostname, int port) {
    logMessage("Pre-populating the key-value store.");
    for (String command : PRE_POPULATION_DATA) {
      sendRequest(hostname, port, command);
    }
    logMessage("Pre-population complete.");
  }

  protected void performAutomaticOperations(String hostname, int port) {
    logMessage("Performing automatic operations...");

    List<String> operations = Arrays.asList(
            "put angel white",
            "put martin king",
            "put kayla holmes",
            "put karina kaif",
            "put dan bilt",
            "get martin",
            "get aditya",
            "get dan",
            "get kayla",
            "get ishan",
            "delete dan",
            "delete kayla",
            "delete martin",
            "delete karina",
            "delete dnyanesh"
    );

    for (String command : operations) {
      sendRequest(hostname, port, command);
    }

    logMessage("Automatic operations complete.");
  }

  protected void acceptUserCommands(String hostname, int port) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter commands (type 'exit' to quit):");

    while (true) {
      System.out.print("Enter Command: ");
      String command = scanner.nextLine();
      if ("exit".equalsIgnoreCase(command)) {
        break;
      }
      sendRequest(hostname, port, command);
    }
  }

  protected static void logMessage(String message) {
    System.out.println(System.currentTimeMillis() + " : " + message);
    ClientLogger.log(message);
  }
}