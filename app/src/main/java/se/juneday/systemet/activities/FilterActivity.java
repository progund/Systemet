package se.juneday.systemet.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import se.juneday.systemet.R;
import se.juneday.systemet.domain.Product;
import se.juneday.systemet.domain.ProductPredicate;
import se.juneday.systemet.domain.ProductPredicate.OPERATIONS;
import se.juneday.systemet.domain.ProductPredicate.VARIABLES;
import se.juneday.systemet.storage.ProductStoreFactory;
import se.juneday.systemet.storage.ProductUtil;

public class FilterActivity extends AppCompatActivity {

  private static final String LOG_TAG = FilterActivity.class.getName();
  private FilterArrayAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_filter);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    ListView listview = (ListView) findViewById(R.id.filters);

    List<ProductPredicate> predicates = new ArrayList<>();
    predicates.add(new ProductPredicate(VARIABLES.ALCOHOL, OPERATIONS.GT));
    adapter = new FilterArrayAdapter(this, predicates);
    listview.setAdapter(adapter);
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.filter_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();


    //noinspection SimplifiableIfStatement
    if (id == R.id.action_new) {
      Toast.makeText(FilterActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
      adapter.add(new ProductPredicate());
      return true;
    } else if (id == R.id.action_calc) {

      Predicate<Product> predicates = (p) -> true;
      Log.d(LOG_TAG, " predicates: " + adapter.getCount());
      for(int i=0 ; i<adapter.getCount() ; i++){
        ProductPredicate prodPred= (ProductPredicate) adapter.getItem(i);
        prodPred.setValue("13");
        predicates = predicates.and(prodPred);
        Log.d(LOG_TAG, " * " + prodPred);
      }
      List<Product> products = ProductStoreFactory.productStore(null).products();
      Log.d(LOG_TAG, " * products: " + products.size());
      products = ProductUtil.getProductsFilteredBy(products, predicates);
      Log.d(LOG_TAG, " * products: " + products.size());
      return true;

    }

    return super.onOptionsItemSelected(item);
  }
}
