package se.juneday.systemet.expandable;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import java.util.List;
import se.itu.systemet.domain.Product;
import se.juneday.systemet.R;

/**
 * Created by hesa on 2017-09-23.
 */

public class ExpandableProductAdapter extends BaseExpandableListAdapter {

  private List<Product> elements;
  public LayoutInflater inflater;
  public Activity activity;

  private final static String LOG_TAG = ExpandableProductAdapter.class.getSimpleName();

  public ExpandableProductAdapter(Activity activity, List<Product> elements) {
    this.activity = activity;
    this.elements = elements;
    inflater      = activity.getLayoutInflater();

//    Log.d(LOG_TAG, "Adding elements: " + elements.size());
  }

  @Override
  public Object getChild(int groupPosition, int childPosition) {
    Log.d(LOG_TAG, "getChild " + groupPosition + " " + childPosition);
    StringBuilder sb      = new StringBuilder();

    Product p = elements.get(groupPosition);
    return " * " + p.name() + "\n" + p.alcohol() + "\n"
        + "bang: "
        + p.alcohol()*p.volume()/p.price();

    //return elements.get(groupPosition).getName();
  }

  @Override
  public long getChildId(int groupPosition, int childPosition) {
    return 0;
  }

  @Override
  public View getChildView(int groupPosition, final int childPosition,
      boolean isLastChild, View convertView, ViewGroup parent) {

    Log.d(LOG_TAG, "getChildView()");
    final String children = (String) getChild(groupPosition, childPosition);
    TextView text = null;
    if (convertView == null) {
      convertView = inflater.inflate(R.layout.product_details, null);
    }
    text = (TextView) convertView.findViewById(R.id.elist_name);
    text.setText(children);
    return convertView;
  }


  @Override
  public int getChildrenCount(int groupPosition) {
    return 1;
  }

  @Override
  public Object getGroup(int groupPosition) {
    return elements.get(groupPosition);
  }

  @Override
  public int getGroupCount() {
    if (elements==null) return 0;
    return elements.size();
  }

  @Override
  public void onGroupCollapsed(int groupPosition) {
    super.onGroupCollapsed(groupPosition);
  }

  @Override
  public void onGroupExpanded(int groupPosition) {
    super.onGroupExpanded(groupPosition);
  }

  @Override
  public long getGroupId(int groupPosition) {
    return 0;
  }

  @Override
  public View getGroupView(int groupPosition, boolean isExpanded,
      View convertView, ViewGroup parent) {
   // Log.d(LOG_TAG, "getGroupView()");
    if (convertView == null) {
      convertView = inflater.inflate(R.layout.layout, null);
    }

    Product element = (Product) getGroup(groupPosition);
    String groupInfo = element.name() + " (" + element.group() + ")";
    ((CheckedTextView) convertView).setText(groupInfo);


    return convertView;
  }

  @Override
  public boolean hasStableIds() {
    return false;
  }

  @Override
  public boolean isChildSelectable(int groupPosition, int childPosition) {
    return false;
  }


}
