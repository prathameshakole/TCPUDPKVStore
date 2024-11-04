package server;

/**
 * StoreOperations is an interface that defines the basic operations for a key-value store.
 * Any class implementing this interface will provide mechanisms to store, retrieve,
 * and delete key-value pairs.
 *
 * It is designed to be flexible and can be used in various types of data stores,
 * such as in-memory stores, file-based stores, or database-backed stores.
 *
 * Operations Provided:
 *   put: Stores a key-value pair in the data store.
 *   get: Retrieves the value associated with a given key.
 *   delete: Deletes a key-value pair from the data store.
 *
 */
public interface StoreOperations {

  /**
   * Stores a key-value pair in the data store.
   *
   * If the key already exists, its value will be overwritten with the new value.
   * If the key is new, it will be added to the store.
   *
   * @param keyValue The KeyValue object to be stored.
   * @return A Response indicating the result of the operation.
   */
  Response putPair(KeyValue keyValue);

  /**
   * Retrieves the value associated with the specified key.
   *
   * <p>If the key exists in the data store, its corresponding value will be returned.
   * If the key is not found, an error response will be returned.</p>
   *
   * @param key The key whose associated value is to be retrieved.
   * @return A Response containing the value associated with the key, or an error if the key is not found.
   */
  Response getPair(String key);

  /**
   * Deletes a key-value pair from the data store.
   *
   * <p>If the key exists, the key-value pair will be removed. If the key is not found,
   * an error response will be returned.</p>
   *
   * @param key The key whose key-value pair is to be deleted.
   * @return A Response indicating whether the key-value pair was successfully deleted or not.
   */
  Response deletePair(String key);
}