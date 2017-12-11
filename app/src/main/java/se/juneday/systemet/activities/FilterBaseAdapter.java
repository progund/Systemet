package se.juneday.systemet.activities;

import android.content.Context;
import android.os.Message;
import android.text.InputFilter.AllCaps;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Spinner;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import se.juneday.systemet.R;
import se.juneday.systemet.domain.ProductPredicate;
import se.juneday.systemet.domain.ProductPredicate.OPERATIONS;
import se.juneday.systemet.domain.ProductPredicate.VARIABLES;

/**
 * Created by hesa on 2017-11-13.
 */

public class FilterBaseAdapter extends BaseAdapter {

  private static final String LOG_TAG = FilterBaseAdapter.class.getSimpleName();

  private Context context;
  private List<ProductPredicate> predicates;
  private FilterBaseAdapter me;


  //public constructor
  public FilterBaseAdapter(Context context, List<ProductPredicate> products) {
    this.context = context;
    this.predicates = products;
    me = this;
  }

  @Override
  public int getCount() {
    return predicates.size();
  }

  @Override
  public ProductPredicate getItem(int i) {
    return predicates.get(i);
  }

  @Override
  public long getItemId(int i) {
    return i;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup viewGroup) {
    Log.d(LOG_TAG, "getView ("+position + ", " + convertView +" , ..)");

    // inflate the layout for each list row
    if (convertView == null) {
      convertView = LayoutInflater.from(context).
          inflate(R.layout.filter_row, viewGroup, false);
    }

      // get current item to be displayed
      ProductPredicate currentItem = getItem(position);

    Spinner variableSpinner = (Spinner) convertView.findViewById(R.id.variable_spinner);
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
      variableSpinner.setTag(position);
      variableSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
          Spinner opSpinner = (Spinner) adapterView;
          ProductPredicate p = getItem((Integer) opSpinner.getTag());
          p.setVariable(VARIABLES.valueOf(variableSpinner.getSelectedItem().toString()));
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
      });
      Spinner opSpinner = (Spinner) convertView.findViewById(R.id.op_spinner);
      ArrayAdapter<String> opSpinnerAdapter;
      opSpinnerAdapter = new ArrayAdapter<String>(context,
          android.R.layout.simple_spinner_item,
          Stream.of(ProductPredicate.OPERATIONS.values())
              .map(Enum::name)
              .collect(Collectors.toList()));
      opSpinner.setTag(position);
      Log.d(LOG_TAG, " spinner: " + opSpinner.getId() + "   tag: " + opSpinner.getTag() + "   pos: "
          + position);
      opSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
          Spinner opSpinner = (Spinner) adapterView;
        /*  Log.d(LOG_TAG, "onItemSelected(" + i + " )  view: " + view);
          Log.d(LOG_TAG, "onItemSelected(" + i + " )  view: " + opSpinner);
          Log.d(LOG_TAG, "onItemSelected(" + i + " )  tag: " + opSpinner.getTag());
          Log.d(LOG_TAG, "onItemSelected(" + i + " )  i: " + i);
          Log.d(LOG_TAG, "onItemSelected(" + i + " )  l: " + l);

          Log.d(LOG_TAG, "onItemSelected(" + i + " )  pos: "
              +adapterView.getPositionForView(view));

//          Object item = adapterView.getItemAtPosition((Integer)opSpinner.getTag());
  //        Log.d(LOG_TAG, "onItemSelected(" + i + " ) item: " + item);
*/
          ProductPredicate p = getItem((Integer) opSpinner.getTag());
          p.setOperation(OPERATIONS.valueOf(opSpinner.getSelectedItem().toString()));
/*          for (ProductPredicate pp : predicates) {
            Log.d(LOG_TAG, " * onItemSelect: " + pp);
          }
          */
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
      });
      Log.d(LOG_TAG, " adapter: " + opSpinnerAdapter);
      opSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      opSpinner.setAdapter(opSpinnerAdapter);

      Button removeButton = (Button) convertView.findViewById(R.id.removeButton);
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
          //views.remove(index);
          notifyDataSetChanged();
          Log.d(LOG_TAG, " <-- onClick: removeButton" + view);
        }

      });
    EditText et = (EditText) convertView.findViewById(R.id.pred_value);
    Log.d(LOG_TAG, " value: " + et.getText().toString());
    currentItem.setValue(et.getText().toString());


    //}


    return convertView;
  }

  public void add(ProductPredicate p) {
    predicates.add(p);
    notifyDataSetChanged();
  }

  public void remove(ProductPredicate p) {
    predicates.remove(p);
    notifyDataSetChanged();
  }

}
