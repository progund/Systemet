package se.juneday.systemet;

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
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import se.itu.systemet.domain.Product;
import se.juneday.systemet.expandable.ExpandableProductAdapter;

public class ExpandableListActivity extends AppCompatActivity {

  private final static String LOG_TAG = ExpandableListActivity.class.getSimpleName();

  private List<Product> products;
  private ExpandableListView mListView;
  private ExpandableProductAdapter mAdapter;
  private ExpandableListActivity me;


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

    products = ProductStore.getInstance(this).products();
    if (products==null) {
      Log.d(LOG_TAG, "nr products: 0");
    } else {
      Log.d(LOG_TAG, "nr products: " + products.size());
    }

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
        if ( (!alcohol.equals("")) && (!price.equals(""))) {
          Log.d(LOG_TAG, "onClick() both");
          products = ProductStore.getInstance(null)
              .getProductLine()
              .getProductsFilteredBy(
                  p -> p.alcohol() > Integer.parseInt(alcohol) &&
                      p.price() < Integer.parseInt(price) );
        } else if (!alcohol.equals("")) {

          Log.d(LOG_TAG, "onClick() alcohol");
          products = ProductStore.getInstance(null)
              .getProductLine()
              .getProductsFilteredBy(
                  p -> p.alcohol() > Integer.parseInt(alcohol));
        } else if (!price.equals("")) {
          Log.d(LOG_TAG, "onClick() price");
          products = ProductStore.getInstance(null)
              .getProductLine()
              .getProductsFilteredBy(
                  p -> p.price() < Integer.parseInt(price));
        }
        mAdapter = new ExpandableProductAdapter(me, products);
        mListView.setAdapter(mAdapter);
        registerForContextMenu(mListView);

        Log.d(LOG_TAG, "onClick() products: " + products.size());

      }
    });

  }

}