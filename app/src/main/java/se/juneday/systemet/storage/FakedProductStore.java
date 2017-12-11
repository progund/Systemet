package se.juneday.systemet.storage;

import android.content.Context;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import se.juneday.systemet.domain.Product;

/**
 * Created by hesa on 2017-11-04.
 */

public class FakedProductStore implements ProductStore {

  private static final String LOG_TAG = FakedProductStore.class.getName();

  private static FakedProductStore store;
  private List<Product> products;
  private List<ProductsChangeListener> listeners;

  public static FakedProductStore getInstance(Context c) {
    if (store == null) {
      store = new FakedProductStore(c);
    }
    return store;
  }

  private FakedProductStore(Context c) {
    listeners = new ArrayList<>();
    products = new ArrayList<>();
    products.add(new Product.Builder().name("Renat").alcohol(43).price(250).volume(700).type("Sprit").build());
    products.add(new Product.Builder().name("Breznac").alcohol(4.3).price(9.9).volume(33).type("Öl").build());
    products.add(new Product.Builder().name("Urquell").alcohol(4.4).price(14.9).volume(33).type("Öl").build());
    products.add(new Product.Builder().name("Ardbeg").alcohol(40).price(350).volume(700).type("Whisky").build());
  }

  @Override
  public List<Product> products() {
    return products;
  }

  @Override
  public void syncProducts() {
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    for (ProductsChangeListener l : listeners) {
      l.productsChanged();
    }
  }

  @Override
  public void close() {


  }

  @Override
  public void addProductsChangeListener(ProductsChangeListener listener) {
    listeners.add(listener);
  }
}
