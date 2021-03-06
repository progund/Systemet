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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import se.juneday.systemet.R;
import se.juneday.systemet.domain.ProductPredicate;

/**
 * Created by hesa on 2017-11-05.
 */

public class FilterArrayAdapter extends ArrayAdapter<ProductPredicate> {

  private static final String LOG_TAG = FilterArrayAdapter.class.getName();
  private FilterArrayAdapter me;
  private final Context context;
  private List<ProductPredicate> predicates;
  private LayoutInflater inflater;

  private Map<Integer, View> views;

  /*  private class PPV {
    ProductPredicate p;
    View v;
  }
  */
  public FilterArrayAdapter(Context context, List<ProductPredicate> predicates) {
    super(context, R.layout.filter_row, /*R.id.value,*/ predicates);
    Log.d(LOG_TAG, "FilterArrayAdapter constructor");
    this.context = context;
    Log.d(LOG_TAG, " values: " + predicates.size());
    me = this;
    this.predicates = predicates;
    views = new HashMap<>();
    inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  /*
  @Override
  public ProductPredicate getItem(int position) {
    Log.d(LOG_TAG, " getItem(" + position + ") ==> " + predicates.get(position));
    return predicates.get(position);
  }
  */

/*  @Override
  public long getItemId(int position) {
    return predicates.get(position).Id;
    //return getItem(position).hashCode();
  }
*/
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    Log.d(LOG_TAG, " ------> getView position: " + position + "  checking views:" + (convertView==null?"null":convertView.hashCode()) + " " + convertView) ;

    if (convertView==null) {
      Log.d(LOG_TAG, " getView NULL");

      View rowView = inflater.inflate(R.layout.filter_row, null);

      Log.d(LOG_TAG, " rowView: " + rowView);
      Log.d(LOG_TAG, " parent: " + parent);
/*
      Spinner variableSpinner = (Spinner)findViewById(R.id.variable_spinner);
      Log.d(LOG_TAG, " spinner: " + variableSpinner);
      Log.d(LOG_TAG, " view: " + (EditText)findViewById(R.id.value));

  */

      EditText text = (EditText) rowView.findViewById(R.id.pred_value);
      Log.d(LOG_TAG, "value view: " + text.getText().toString());
/*
      Spinner variableSpinner = (Spinner) rowView.findViewById(R.id.variable_spinner);
      Log.d(LOG_TAG, " spinner: " + variableSpinner);

      ArrayAdapter<String> spinnerAdapter;
      spinnerAdapter = new ArrayAdapter<String>(context,
          android.R.layout.simple_spinner_item,
          Stream.of(ProductPredicate.VARIABLES.values())
              .map(Enum::name)
              .collect(Collectors.toList()));
      Log.d(LOG_TAG, " adapter: " + spinnerAdapter);
      spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      variableSpinner.setAdapter(spinnerAdapter);

      Spinner opSpinner = (Spinner) rowView.findViewById(R.id.op_spinner);
      ArrayAdapter<String> opSpinnerAdapter;
      opSpinnerAdapter = new ArrayAdapter<String>(context,
          android.R.layout.simple_spinner_item,
          Stream.of(ProductPredicate.OPERATIONS.values())
              .map(Enum::name)
              .collect(Collectors.toList()));
      Log.d(LOG_TAG, " adapter: " + opSpinnerAdapter);
      opSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      opSpinner.setAdapter(opSpinnerAdapter);

      Button removeButton = (Button) rowView.findViewById(R.id.removeButton);
      removeButton.setTag(position);
      Log.d(LOG_TAG, " removeButton: " + removeButton + " <----");

      removeButton.setOnClickListener(new OnClickListener() {

        @Override
        public void onClick(View view) {
          Log.d(LOG_TAG, " --> onClick: removeButton" + view);
          Log.d(LOG_TAG, "      parent: " + view.getParent());
          Log.d(LOG_TAG, "      p parent: " + view.getParent().getParent());
//        ((AdapterView)view.getParent().getParent()).removeView((View)view.getParent());
          Integer index = (Integer) view.getTag();
          Log.d(LOG_TAG, "      index: " + index);
          me.predicates.remove(index.intValue());
          Log.d(LOG_TAG, " views.remove(" + position + ")");
          views.remove(index);
          notifyDataSetChanged();
          Log.d(LOG_TAG, " <-- onClick: removeButton" + view);
        }

      });

      rowView.setId(rowView.hashCode());
      Log.d(LOG_TAG, " views.put(" + position + ",  " + rowView + ")    hashcode: "  + rowView.getId());
      views.put(position, rowView);
      Log.d(LOG_TAG, " views. contains: " + views.size());
      convertView = rowView;
*/
    }

    Log.d(LOG_TAG, " <------ getView position: " + position + "  checking views.");
    return convertView;
  }

/*
  @Override
  public int getCount() {
    Log.d(LOG_TAG, " <------ getCount: " + predicates.size());
    return predicates.size();
  }
*/

  public View getView(int pos) {
    Log.d(LOG_TAG, " views.get(" + pos + ") --->  " + views.get(pos));
    Log.d(LOG_TAG, " getView(" + pos +") ===> ");
    Log.d(LOG_TAG, "      ==> " + views.get(pos).hashCode());
    return views.get(pos);
  }

  @Override
  public void add(ProductPredicate p) {
    predicates.add(p);
    notifyDataSetChanged();
  }

  @Override
  public void remove(ProductPredicate p) {
    predicates.remove(p);
    notifyDataSetChanged();
  }

  /*
  @Override
  public Context getContext(){
    return context;
  }
  */

}
