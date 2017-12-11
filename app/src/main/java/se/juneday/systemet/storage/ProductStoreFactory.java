package se.juneday.systemet.storage;

import android.content.Context;

/**
 * Created by hesa on 2017-11-04.
 */

public enum ProductStoreFactory {

  INSTANCE;
  private static ProductStore store;

  public static ProductStore productStore(Context c) {
    if (store==null) {
      store = JsonProductStore.getInstance(c);
//      store = FakedProductStore.getInstance(c);
    }
    return store;
  }


}
