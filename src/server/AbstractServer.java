package server;

public abstract class AbstractServer {
  protected static final StoreOperations dataStore = new StoreOperationsImpl();

  protected static Response processRequest(String request) {
    String[] parts = request.split(" ");
    String command = parts[0].toLowerCase();

    switch (command) {
      case "put":
        return handlePut(parts);
      case "get":
        return handleGet(parts);
      case "delete":
        return handleDelete(parts);
      default:
        return Response.error("Invalid command");
    }
  }

  private static Response handlePut(String[] command) {
    if (command.length == 3) {
      KeyValue keyValue = new KeyValue(command[1], command[2]);
      return dataStore.putPair(keyValue);
    } else {
      return Response.error("Incorrect command");
    }
  }

  private static Response handleGet(String[] command) {
    if (command.length == 2) {
      return dataStore.getPair(command[1]);
    } else {
      return Response.error("Incorrect command");
    }
  }

  private static Response handleDelete(String[] command) {
    if (command.length == 2) {
      return dataStore.deletePair(command[1]);
    } else {
      return Response.error("Incorrect command");
    }
  }

  protected static void logMessage(String message) {
    System.out.println(System.currentTimeMillis() + " : " + message);
    ServerLogger.log(message);
  }
}