package se.juneday.systemet.storage;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import se.juneday.systemet.domain.Product;

/**
 * Created by hesa on 2017-11-04.
 */

public class ProductUtil {

  private static final String LOG_TAG = ProductUtil.class.getName();

  public static List<Product> getProductsFilteredBy(List <Product> products, Predicate<Product> predicate) {
    List<Product> list = products.stream().filter(predicate).collect(Collectors.toList());
    //Log.d(LOG_TAG, " getProductsFilteredBy(): " + products.size() + " => " + list.size());
    return list;
  }

  public interface ProductFilter { public boolean keep(Product product);}

  public static List<Product> filter(List <Product> products, ProductFilter filter) {
    List<Product> list = new ArrayList<>();
    for (Product product : products) {
      if (filter.keep(product)) {
        list.add(product);
      }
    }
    return list;
  }

}
