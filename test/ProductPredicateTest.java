import android.util.Log;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import se.juneday.systemet.domain.Product;
import se.juneday.systemet.domain.ProductPredicate;
import se.juneday.systemet.storage.ProductStore;
import se.juneday.systemet.storage.ProductUtil;
import se.juneday.systemet.storage.FakedProductStore;

public class ProductPredicateTest{

  private static final String LOG_TAG = ProductPredicateTest.class.getName();

  public static void main(String[] args) {
    List <Product> products;

    List <ProductPredicate> productPredicates =
      new ArrayList<>();

    ProductStore store =
      FakedProductStore.getInstance(null);

    productPredicates.add(new ProductPredicate(ProductPredicate.VARIABLES.PRICE,
                                               ProductPredicate.OPERATIONS.GT,
                                               "5"));
    
    productPredicates.add(new ProductPredicate(ProductPredicate.VARIABLES.PRICE,
                                               ProductPredicate.OPERATIONS.LT,
                                               "200"));
    
    productPredicates.add(new ProductPredicate(ProductPredicate.VARIABLES.TYPE,
                                               ProductPredicate.OPERATIONS.EQ,
                                               "Öl"));
    
    //    predicates.add((p) -> true);
    Predicate<Product> predicates = (p) -> true;
    
    for (ProductPredicate predicate : productPredicates) {
      predicates = predicates.and(predicate);
      //      System.out.println(" * " + predicate);
    }

    products = store.products();
    Log.d(LOG_TAG, " * products: " + products.size());
    products = ProductUtil.getProductsFilteredBy(products, predicates);
    Log.d(LOG_TAG, " * products: " + products.size());
    Log.d(LOG_TAG, " * products: " + products);

  }

  
}
