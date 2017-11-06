package se.juneday.systemet.activities;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import se.juneday.systemet.R;

/**
 * Created by hesa on 2017-11-05.
 */

public class FilterArrayAdapter extends ArrayAdapter<String> {

  private static final String LOG_TAG = FilterArrayAdapter.class.getName();
  private FilterArrayAdapter me;
  private final Context context;

  private static List<String> values = Arrays
      .asList(new String[]{"Price", "Alcohol", "Type", "Tycho", "Brahe"});

  private static List<String> operations = Arrays.asList(new String[]{"<", ">", "="});

  public FilterArrayAdapter(Context context, List<String> initial) {
    super(context, R.layout.filter_row, initial );
    this.context = context;
    Log.d(LOG_TAG, " values: " + values.size());
    me = this;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {

    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    View rowView = inflater.inflate(R.layout.filter_row, null);

    Log.d(LOG_TAG, " rowView: " + rowView);
    Log.d(LOG_TAG, " parent: " + parent);
/*
      Spinner variableSpinner = (Spinner)findViewById(R.id.variable_spinner);
      Log.d(LOG_TAG, " spinner: " + variableSpinner);
      Log.d(LOG_TAG, " view: " + (EditText)findViewById(R.id.value));

  */

    EditText text = (EditText)rowView.findViewById(R.id.value);
    Log.d(LOG_TAG, " view: " + text);

    Spinner variableSpinner = (Spinner)rowView.findViewById(R.id.variable_spinner);
    Log.d(LOG_TAG, " spinner: " + variableSpinner);

    ArrayAdapter<String> spinnerAdapter;
    spinnerAdapter = new ArrayAdapter<String>(context,
        android.R.layout.simple_spinner_item, values);
    Log.d(LOG_TAG, " adapter: " + spinnerAdapter);
    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    variableSpinner.setAdapter(spinnerAdapter);

    Spinner opSpinner = (Spinner)rowView.findViewById(R.id.op_spinner);
    ArrayAdapter<String> opSpinnerAdapter;
    opSpinnerAdapter= new ArrayAdapter<String>(context,
        android.R.layout.simple_spinner_item, operations);
    Log.d(LOG_TAG, " adapter: " + opSpinnerAdapter);
    opSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    opSpinner.setAdapter(opSpinnerAdapter);

    Button removeButton = (Button) rowView.findViewById(R.id.removeButton);
    removeButton.setTag(position);
    Log.d(LOG_TAG, " removeButton: " + removeButton);

    removeButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Log.d(LOG_TAG, " onClick: removeButton" + view );
        Log.d(LOG_TAG,"    parent: " + view.getParent());
        Log.d(LOG_TAG,"    p parent: " + view.getParent().getParent());
//        ((AdapterView)view.getParent().getParent()).removeView((View)view.getParent());
        Integer index = (Integer) view.getTag();
        Log.d(LOG_TAG,"    index: " + index);
        values.remove(index.intValue());
        notifyDataSetChanged();

      }
    });

    return rowView;
  }
}
