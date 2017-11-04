package se.juneday.systemet.storage;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import se.juneday.systemet.domain.Product;

/**
 * Created by hesa on 2017-11-04.
 */

public class ProductUtil {

  public static List<Product> getProductsFilteredBy(List <Product> products, Predicate<Product> predicate) {
    return products.stream().filter(predicate).collect(Collectors.toList());
  }


}
