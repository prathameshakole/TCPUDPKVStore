package server;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class ServerLogger {

  private static final Logger logger = Logger.getLogger(ServerLogger.class.getName());
  private static FileHandler fileHandler;

  static {
    try {
      fileHandler = new FileHandler("server_log.txt", true);
      logger.addHandler(fileHandler);
      CustomFormatter formatter = new CustomFormatter();
      fileHandler.setFormatter(formatter);
      logger.setUseParentHandlers(false);
    } catch (IOException e) {
      System.err.println("Failed to initialize log file handler: " + e.getMessage());
    }
  }

  public static void log(String message) {
    logger.log(Level.INFO, message);
  }

  public static void close() {
    if (fileHandler != null) {
      fileHandler.close();
    }
  }

  private static class CustomFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
      return System.currentTimeMillis() + ": " + record.getMessage() + "\n";
    }
  }

}
