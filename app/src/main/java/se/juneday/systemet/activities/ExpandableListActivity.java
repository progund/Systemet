package se.juneday.systemet.activities;

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
import se.juneday.systemet.storage.ProductStore;
import se.juneday.systemet.storage.ProductStoreFactory;
import se.juneday.systemet.storage.ProductUtil;
import se.juneday.systemet.storage.ProductsChangeListener;

public class ExpandableListActivity extends AppCompatActivity {

  private final static String LOG_TAG = ExpandableListActivity.class.getSimpleName();

  private List<Product> products;
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
      }
    });
    mListView = (ExpandableListView) findViewById(R.id.expandable_list);

    Properties props = System.getProperties();
    String sortimentDir = Environment.getExternalStorageDirectory().getAbsolutePath();
    String sortimentFile= "/Download/sortiment.xml";
    String tmpFile= "/Download/sortiment1.xml";
    props.setProperty("sortiment-xml-file", sortimentDir + sortimentFile);
    props.setProperty("product-line-class", "se.itu.systemet.storage.XMLRawProductLineFactory");


    /*
    try {
      XMLUtil.fixUTF8File(sortimentDir, sortimentFile, tmpFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
    */
  }


  public void onStart() {
    super.onStart();

    store = ProductStoreFactory.productStore(this);

    store.addProductsChangeListener(new ProductsChangeListener() {
      @Override
      public void productsChanged() {
        Log.d(LOG_TAG, "productsChanged()");
        mAdapter = new ExpandableProductAdapter(me, products);
        mListView.setAdapter(mAdapter);
      }
    });
    store.syncProducts();

    products = store.products();
    if (products==null) {
      Log.d(LOG_TAG, "nr products: 0");
    } else {
      Log.d(LOG_TAG, "nr products: " + products.size());
    }

//    Collections.sort(products, Product.BANG_FOR_BUCK_ORDER);

    mAdapter = new ExpandableProductAdapter(this, products);
    mListView.setAdapter(mAdapter);
    registerForContextMenu(mListView);

    Button okButton = findViewById(R.id.ok_button);
    okButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Log.d(LOG_TAG, "onClick()");
        String alcohol = ((EditText)findViewById(R.id.alcohol_entry)).getText().toString();
        String price = ((EditText)findViewById(R.id.price_entry)).getText().toString();
        String group = ((EditText)findViewById(R.id.group_entry)).getText().toString();

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
        products = ProductUtil.getProductsFilteredBy(store.products(), predicate);

        mAdapter = new ExpandableProductAdapter(me, products);
        mListView.setAdapter(mAdapter);
        registerForContextMenu(mListView);

        Log.d(LOG_TAG, "onClick() products: " + products.size());
        Log.d(LOG_TAG, "onClick() predicate: " + predicate);

      }
    });

  }

}