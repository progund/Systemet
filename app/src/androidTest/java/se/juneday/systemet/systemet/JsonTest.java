package se.juneday.systemet.systemet;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import android.util.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import se.juneday.systemet.storage.JsonProductStore;
import se.juneday.systemet.storage.ProductStore;
import se.juneday.systemet.storage.ProductsChangeListener;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class JsonTest implements ProductsChangeListener {

  private static final String LOG_TAG = JsonTest.class.getName();
  private boolean changeOccured;

  private ProductStore store;

  {
    store = JsonProductStore.getInstance(InstrumentationRegistry.getTargetContext());
    store.syncProducts();
    store.addProductsChangeListener(this);
  }

  private static final int SECONDS = 5;

  @Test
  public void useJsonProductStore() throws Exception {
    Log.d(LOG_TAG, " ---> useJsonProductStore()");
    for (int i=0; i<SECONDS; i++) {
      Log.d(LOG_TAG, " useJsonProductStore sleep: " + i + " (of " + SECONDS + ")");
    }
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    assert(store.products().size()>100);
    Log.d(LOG_TAG, " products: " + store.products().size());
    Log.d(LOG_TAG, " <--- useJsonProductStore()");
  }

  @Test
  public void productsChangedInvoked() throws Exception {
    Log.d(LOG_TAG, " --> productsChangedInvoked()");
    for (int i=0; i<SECONDS; i++) {
      Log.d(LOG_TAG, " productsChangedInvoked sleep: " + i + " (of " + SECONDS + ")");
    }
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    assert(changeOccured);
    Log.d(LOG_TAG, " <-- productsChangedInvoked()");
  }

  @Override
  public void productsChanged() {
    changeOccured=true;
  }
}
