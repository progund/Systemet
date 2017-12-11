package se.juneday.systemet.activities;

import android.app.usage.NetworkStatsManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import java.util.List;
import java.util.Properties;
import java.util.function.Predicate;
import se.juneday.systemet.R;
import se.juneday.systemet.domain.Product;
import se.juneday.systemet.activities.FilterActivity;
import se.juneday.systemet.domain.ProductPredicate;
import se.juneday.systemet.storage.JsonProductStore;
import se.juneday.systemet.storage.ProductStore;
import se.juneday.systemet.storage.ProductStoreFactory;
import se.juneday.systemet.storage.ProductUtil;
import se.juneday.systemet.storage.ProductUtil.ProductFilter;
import se.juneday.systemet.storage.ProductsChangeListener;
import se.juneday.systemet.utils.NetworkChecker;
import se.juneday.systemet.utils.NetworkChecker.NetworkCheckerListener;
import se.juneday.systemet.utils.Session;

public class ExpandableListActivity extends AppCompatActivity {

  private final static String LOG_TAG = ExpandableListActivity.class.getSimpleName();

  private ExpandableListView mListView;
  private ExpandableProductAdapter mAdapter;
  private ExpandableListActivity me;
  private ProductStore store;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_expandable_list);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    me = this;

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
        Intent intent = new Intent(me, SimpleFilterActivity.class);
        startActivity(intent);
      }
    });

    mListView = (ExpandableListView) findViewById(R.id.expandable_list);

/*    Properties props = System.getProperties();
    String sortimentDir = Environment.getExternalStorageDirectory().getAbsolutePath();
    String sortimentFile= "/Download/sortiment.xml";
    String tmpFile= "/Download/sortiment1.xml";
    props.setProperty("sortiment-xml-file", sortimentDir + sortimentFile);
    props.setProperty("product-line-class", "se.itu.systemet.storage.XMLRawProductLineFactory");
*/
    /*
    try {
      XMLUtil.fixUTF8File(sortimentDir, sortimentFile, tmpFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
    */
  }

  private void updateProductList(List<Product> list) {
    Log.d(LOG_TAG, " updateProductList(): " + list.size());
    mAdapter = new ExpandableProductAdapter(me, list);
    mListView.setAdapter(mAdapter);
  }

  private void updateProductList() {
    updateProductList(store.products());
  }

  private String textFieldToString(int id) {
    return ((EditText)findViewById(id)).getText().toString();
  }

  public void onStart() {
    super.onStart();

    store = ProductStoreFactory.productStore(this);

    store.addProductsChangeListener(new ProductsChangeListener() {
      @Override
      public void productsChanged() {
        Log.d(LOG_TAG, "productsChanged()");
        updateProductList();
      }
    });
    store.syncProducts();

    updateProductList();
    registerForContextMenu(mListView);

    List<ProductPredicate> predicates = Session.getInstance(this).getProductPredicates();
    if (predicates!=null) {
      for (ProductPredicate p : predicates) {
        Log.d(LOG_TAG, " * predicate: " + p);
      }
    }

    Button okButton = findViewById(R.id.ok_button);
    okButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Log.d(LOG_TAG, "onClick()");
        String alcohol = textFieldToString(R.id.alcohol_entry);
        String price = textFieldToString(R.id.price_entry);
        String group = textFieldToString(R.id.group_entry);

        // TODO: is there a better (read sane) way to create an "empty" Predicate
        Predicate<Product> predicate = (p) -> true;

        if (!alcohol.equals("")) {
          Log.d(LOG_TAG, "onClick() alcohol");
          predicate = predicate.and(p -> p.alcohol() > Integer.parseInt(alcohol));
        }
        if (!price.equals("")) {
          Log.d(LOG_TAG, "onClick() price");
          predicate = predicate.and(p -> p.price() < Integer.parseInt(price));
        }
        if (!group.equals("")) {
          Log.d(LOG_TAG, "onClick() group: " + group);
          predicate = predicate.and(p -> p.type().contains(group));
        }
        List<Product> products = ProductUtil.getProductsFilteredBy(store.products(), predicate);

/*      Example on Using anonymous inner class
        products = ProductUtil.filter(store.products(), new ProductFilter() {

          @Override
          public boolean keep(Product product) {
            int max = Integer.parseInt(price);
            return product.price() < max ;
          }
        });
*/
        updateProductList(products);
      }
    });




  }

  public void onPause() {
    super.onPause();
    Log.d(LOG_TAG, "onPause()");
    JsonProductStore.getInstance(this).close();
  }

}