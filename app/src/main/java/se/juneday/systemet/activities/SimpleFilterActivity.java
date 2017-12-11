package se.juneday.systemet.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import se.juneday.systemet.R;
import se.juneday.systemet.domain.Product;
import se.juneday.systemet.domain.ProductPredicate;
import se.juneday.systemet.domain.ProductPredicate.OPERATIONS;
import se.juneday.systemet.domain.ProductPredicate.VARIABLES;
import se.juneday.systemet.utils.Session;

public class SimpleFilterActivity extends AppCompatActivity {

  private static final String LOG_TAG = SimpleFilterActivity.class.getSimpleName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_simple_filter);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
/*
    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
      }
    });
    */
  }

  public void onStart() {
    super.onStart();
  }


  public void onPause() {
    super.onPause();
    Log.d(LOG_TAG, "onPause()");
    List<ProductPredicate> list = new ArrayList<>();
    list.add(
          new ProductPredicate(ProductPredicate.VARIABLES.ALCOHOL, OPERATIONS.GT, "12"));

    Session.getInstance(this).setProductPredicates(list);
    Session.getInstance(this).close();
    Log.d(LOG_TAG, "onPause(): predicates: " +     Session.getInstance(this).getProductPredicates());

  }

}
