package se.juneday.systemet.systemet;

import android.util.Log;
import java.util.List;
import org.junit.Test;
import se.juneday.systemet.domain.Product;
import se.juneday.systemet.storage.FakedProductStore;
import se.juneday.systemet.storage.ProductStore;
import se.juneday.systemet.storage.ProductUtil;
import se.juneday.systemet.storage.ProductsChangeListener;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ProductUtiltest implements ProductsChangeListener{

  private static final String LOG_TAG = ProductUtiltest.class.getName();
  private ProductStore store;
  private boolean changeOccured;
  private int SECONDS = 5;

  {
    store = FakedProductStore.getInstance(null);
    store.syncProducts();
    store.addProductsChangeListener(this);
  }


  @Test
  public void testProducts() throws Exception {
    for (int i=0; i<SECONDS; i++) {
      ;
    }
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    int size = store.products().size();
    assertEquals(size, 3);
  }

  @Test
  public void testProductsFilterType() throws Exception {
    int size = ProductUtil.getProductsFilteredBy(store.products(), p -> p.type().equals("Öl")).size();
    assertEquals(1, size);
  }

  @Test
  public void testProductsFilterAlcohol() throws Exception {
    int size = ProductUtil.getProductsFilteredBy(store.products(), p -> p.alcohol() > 40).size();
    assertEquals(1, size);
  }

  @Test
  public void testProductsFilterAlcohol2() throws Exception {
    int size = ProductUtil.getProductsFilteredBy(store.products(), p -> p.alcohol() > 43).size();
    assertEquals(0, size);
  }

  @Test
  public void testProductsFilterPrice() throws Exception {
    int size = ProductUtil.getProductsFilteredBy(store.products(), p -> p.price() > 10).size();
    assertEquals(2, size);
  }

  @Test
  public void testProductsFilterPrice2() throws Exception {
    int size = ProductUtil.getProductsFilteredBy(store.products(), p -> p.price() > 500).size();
    assertEquals(0, size);
  }

  @Test
  public void testChangeOccured() throws Exception {
    assert(changeOccured);
  }

  @Override
  public void productsChanged() {
    changeOccured=true;
  }
}