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
import java.util.Map;
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
//  private FilterArrayAdapter adapter;
  private FilterBaseAdapter adapter;
  private ListView listview;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_filter);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);


    listview = (ListView) findViewById(R.id.filters);

    List<ProductPredicate> predicates = new ArrayList<>();
    predicates.add(new ProductPredicate(VARIABLES.ALCOHOL, OPERATIONS.GT));
    adapter = new FilterBaseAdapter(this, predicates);
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
        ProductPredicate prodPred = (ProductPredicate) adapter.getItem(i);
        View view = adapter.getView(i, null, listview);
        //View view = adapter.getView(i);

        // TODO: bring back the below
        String var = ((Spinner)view.findViewById(R.id.variable_spinner)).getSelectedItem().toString();
        String op = ((Spinner)view.findViewById(R.id.op_spinner)).getSelectedItem().toString();
        String val = ((EditText)view.findViewById(R.id.pred_value)).getText().toString();

        Log.d(LOG_TAG, " * view: " + view);
        Log.d(LOG_TAG, " * valueView: " + ((EditText)view.findViewById(R.id.pred_value)));
        Log.d(LOG_TAG, " * valueView: " + ((EditText)view.findViewById(R.id.pred_value)).getText());
        Log.d(LOG_TAG, " * spinner: " + var);
        Log.d(LOG_TAG, " * operation: " + op);
        Log.d(LOG_TAG, " * operation: " + prodPred);
        Log.d(LOG_TAG, " * value: '" + val + "'");
        prodPred.setValue(val);
     //   prodPred.setVariable(VARIABLES.valueOf(var));
       // prodPred.setOperation(OPERATIONS.valueOf(op));

        predicates = predicates.and(prodPred);
        Log.d(LOG_TAG, " * " + prodPred);
      }
      List<Product> products = ProductStoreFactory.productStore(null).products();
      Log.d(LOG_TAG, " * products: " + products.size());
   //   products = ProductUtil.getProductsFilteredBy(products, predicates);
      Log.d(LOG_TAG, " * products: " + products.size());
      return true;

    }

    return super.onOptionsItemSelected(item);
  }
}
