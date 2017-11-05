package se.juneday.systemet.activities;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import java.util.List;
import se.juneday.systemet.R;

/**
 * Created by hesa on 2017-11-05.
 */

public class FilterArrayAdapter extends ArrayAdapter<String> {

  private static final String LOG_TAG = FilterArrayAdapter.class.getName();

  private final Context context;
  private final List<String> values;

  public FilterArrayAdapter(Context context, int id, List<String> values) {
    super(context, id, values);
    this.context = context;
    this.values = values;
    Log.d(LOG_TAG, " values: " + values.size());
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {

    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    View rowView = inflater.inflate(R.layout.filter_row, null);

    Log.d(LOG_TAG, " rowView: " + rowView);
/*
      Spinner variableSpinner = (Spinner)findViewById(R.id.variable_spinner);
      Log.d(LOG_TAG, " spinner: " + variableSpinner);
      Log.d(LOG_TAG, " view: " + (EditText)findViewById(R.id.value));

      Spinner opSpinner = (Spinner)findViewById(R.id.op_spinner);
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

    return rowView;
  }
}
