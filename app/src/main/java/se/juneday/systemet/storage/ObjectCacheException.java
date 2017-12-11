package se.juneday.systemet.storage;

/**
 * Created by hesa on 2017-12-09.
 */

public class ObjectCacheException extends RuntimeException {
  public ObjectCacheException(String msg, Exception cause) {
    super(msg, cause);
  }
  public ObjectCacheException(String msg) {
    super(msg);
  }
}
