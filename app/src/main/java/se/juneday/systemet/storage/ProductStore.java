package se.juneday.systemet.storage;

import java.util.List;
import se.juneday.systemet.domain.Product;

/**
 * Created by hesa on 2017-11-04.
 */

public interface ProductStore {

  public List<Product> products();
  public void syncProducts();
  public void close();
  public void addProductsChangeListener(ProductsChangeListener listener);

}
