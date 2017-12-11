package se.juneday.systemet.utils;

import android.content.Context;
import java.io.Serializable;
import java.util.List;
import se.juneday.systemet.domain.ProductPredicate;
import se.juneday.systemet.storage.ObjectCache;
import se.juneday.systemet.storage.ObjectCache.CACHE_TYPE;

/**
 * Created by hesa on 2017-12-09.
 */

public class Session {

  public class SessionStore implements Serializable {
    private static final long serialVersionUID = -7107674192607936545L;
    private List<ProductPredicate> productPredicates;
  }

  private static Session instance;

  private Context context;
  private ObjectCache<SessionStore> cache;
  private SessionStore store;

  private Session(Context context) {
    cache = new ObjectCache<>(Session.class, context, CACHE_TYPE.SINGLE);
    cache.pull();
    store = cache.getSingle();
    if (store == null) {
      store = new SessionStore();
    }
  };

  public static synchronized Session getInstance(Context context) {
      if (instance==null) {
        instance = new Session(context);
      }
      return instance;
  }

  public void setProductPredicates(List<ProductPredicate> productPredicates) {
    store.productPredicates=productPredicates;
    cache.setSingle(store);
  }

  public List<ProductPredicate> getProductPredicates() {
    return store.productPredicates;
  }

  public void close() {
    cache.commit();
  }

}
