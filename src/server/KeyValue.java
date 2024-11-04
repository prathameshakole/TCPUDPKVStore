package server;

import static server.AbstractServer.logMessage;

public class KeyValue {
  private String key;
  private String value;

  public KeyValue(String key, String value) {
    if (!key.matches("[a-zA-Z0-9]+")) {
      logMessage("Key must only contain alphanumeric characters (no spaces or special characters)");
    }

    if (key.length() > 256) {
      logMessage("Key cannot be longer than 256 characters");
    }

    if (value == null || value.isEmpty()) {
      logMessage("Value cannot be null or empty");
    }

    if (value.length() > 1000) {
      logMessage("Value cannot be longer than 1000 characters");
    }
    this.key = key;
    this.value = value;
  }

  public String getKey() {
    return key;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return key + ":" + value;
  }
}