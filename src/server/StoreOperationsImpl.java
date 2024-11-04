package server;

import java.util.HashMap;
import java.util.Map;

public class StoreOperationsImpl implements StoreOperations {

  private final Map<String, String> storeMap = new HashMap<>();

  @Override
  public Response putPair(KeyValue keyValue) {
    storeMap.put(keyValue.getKey(), keyValue.getValue());
    return Response.success("Key-Value Pair Saved Successfully!");
  }

  @Override
  public Response getPair(String key) {
    String value = storeMap.get(key);
    if (value != null) {
      return Response.success(new KeyValue(key, value).toString());
    } else {
      return Response.error("Key not found");
    }
  }

  @Override
  public Response deletePair(String key) {
    if (storeMap.remove(key) != null) {
      return Response.success("Key-Value Pair Deleted Successfully!");
    } else {
      return Response.error("Key not found");
    }
  }
}