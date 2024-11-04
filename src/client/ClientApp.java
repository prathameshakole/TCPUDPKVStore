package client;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;

import static client.AbstractClient.logMessage;

public class ClientApp {
  public static void main(String[] args) {
    if (args.length != 3) {
      logMessage("ERROR : Usage: java client.ClientApp <host-name> <port-number> <protocol>");
      return;
    }

    String hostname = args[0];
    int port;
    String protocol = args[2].toUpperCase();

    try {
      port = Integer.parseInt(args[1]);
    } catch (NumberFormatException e) {
      logMessage("ERROR : The port number is invalid.");
      return;
    }

    AbstractClient client;
    switch (protocol) {
      case "TCP":
        client = new TcpClient();
        break;
      case "UDP":
        client = new UdpClient();
        break;
      default:
        logMessage("ERROR : Invalid protocol. Use 'TCP' or 'UDP'.");
        return;
    }

    try {
      client.start(hostname, port, protocol);
    } catch (UnknownHostException e) {
      logMessage("ERROR : Unknown host: " + hostname);
    } catch (ConnectException e) {
      logMessage("ERROR : Unable to connect to the server. " +
              "Please ensure the server is running and reachable.");
    } catch (SocketException e) {
      logMessage("ERROR : Connection lost. The server may have gone offline.");
    } catch (IOException e) {
      logMessage("ERROR : I/O error occurred: " + e.getMessage());
    } catch (Exception e) {
      logMessage("ERROR : An unexpected error occurred: " + e.getMessage());
    }
  }
}
