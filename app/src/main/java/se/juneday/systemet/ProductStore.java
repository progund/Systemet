package se.juneday.systemet;

import android.content.Context;
import android.util.Log;
import java.util.Collections;
import java.util.List;
import se.itu.systemet.domain.Product;
import se.itu.systemet.storage.ProductLine;
import se.itu.systemet.storage.ProductLineFactory;

/**
 * Created by hesa on 2017-09-23.
 */

public class ProductStore {

  private static final String LOG_D = ProductStore.class.getName() ;
  private static ProductStore store;
  private List<Product> products;

  public static ProductStore getInstance(Context c) {
    if (store == null) {
      store = new ProductStore(c);
    }
    return store;
  }

  public List<Product> products() {
    return products;
  }

  private ProductStore(Context c) {
    // Get the ProductLine:
    ProductLine productLine = ProductLineFactory.getProductLine();

    // Get all Products:
//    products = productLine.getProductsFilteredBy(p -> p.alcohol() > 70.0);

    Log.d(LOG_D, " productLine: " + productLine);

    products = productLine.getAllProducts();
    Collections.sort(products, Product.PRICE_ORDER);

    Log.d(LOG_D, " products in store: " + products.size());
  }


}
