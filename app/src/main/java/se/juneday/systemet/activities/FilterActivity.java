package se.juneday.systemet.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import se.juneday.systemet.R;

public class FilterActivity extends AppCompatActivity {

  private static final String LOG_TAG = FilterActivity.class.getName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_filter);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    ListView listview = (ListView) findViewById(R.id.filters);

    List<String> variables = Arrays.asList(new String[] { "Price", "Alcohol", "Type" });

    FilterArrayAdapter adapter = new FilterArrayAdapter(this,
        /*android.R.layout.simple_list_item_1*/ R.layout.filter_row, variables);
    listview.setAdapter(adapter);

  }

}
