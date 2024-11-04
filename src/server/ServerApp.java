package server;

        import java.io.IOException;
        import java.net.DatagramSocket;
        import java.net.ServerSocket;

        import static server.AbstractServer.logMessage;

public class ServerApp {

  public static void main(String[] args) {
    if (args.length != 2) {
      logMessage("ERROR : Usage: java server.ServerApp <protocol> <port-number>");
      return;
    }

    int port;
    String protocol = args[0].toUpperCase();
    try {
      port = Integer.parseInt(args[1]);
    } catch (NumberFormatException e) {
      logMessage("ERROR : Invalid port number.Please provide a valid port.");
      return;
    }

    try {
      if (protocol.equals("TCP")) {
        try (ServerSocket tcpServerSocket = new ServerSocket(port)) {
          TcpServer tcpServer = new TcpServer();
          logMessage("TCP Server started on port " + port);
          tcpServer.start(tcpServerSocket);
        }
      } else if (protocol.equals("UDP")) {
        try (DatagramSocket udpSocket = new DatagramSocket(port)) {
          logMessage("UDP Server is running on port " + port);
          UdpServer udpServer = new UdpServer();
          udpServer.start(udpSocket);
        }
      } else {
        logMessage("ERROR : Invalid protocol. " +
                "Please enter 'TCP' or 'UDP'.");
      }
    } catch (IOException e) {
      logMessage("ERROR : Failed to start the server: " + e.getMessage());
    } catch (Exception e) {
      logMessage("ERROR : An unexpected error occurred: " + e.getMessage());
    }
  }
}
