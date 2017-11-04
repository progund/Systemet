package se.juneday.systemet.storage;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import se.juneday.systemet.domain.Product;
import se.juneday.systemet.domain.Product.Builder;
/*import se.itu.systemet.domain.Product;
import se.itu.systemet.storage.ProductLine;
import se.itu.systemet.storage.ProductLineFactory;
*/

/**
 * Created by hesa on 2017-09-23.
 */

public class JsonProductStore implements ProductStore {

  private static final String LOG_TAG = JsonProductStore.class.getName() ;
  private static JsonProductStore store;
  private List<Product> products;
  private Context context;
  List<ProductsChangeListener> listeners;
//  ProductLine productLine;

  public static synchronized JsonProductStore getInstance(Context c) {
    if (store == null) {
      store = new JsonProductStore(c);
    }
    return store;
  }

  public List<Product> products() {
    return products;
  }

  private JsonProductStore(Context c) {
    context = c;
    products = new ArrayList<>();
    listeners = new ArrayList<>();
    Log.d(LOG_TAG, " products in store: " + products.size());
  }


  public void syncProducts() {
    Log.d(LOG_TAG, "Sync products");
  // Instantiate the RequestQueue.
      RequestQueue queue = Volley.newRequestQueue(context);
  String url ="http://rameau.sandklef.com/all.json";


  JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
      Request.Method.GET,
      url,
      null,
      new Response.Listener<JSONArray>() {

        @Override
        public void onResponse(JSONArray array) {
          for (int i = 0; i < array.length(); i++) {
            try {
              JSONObject row = array.getJSONObject(i);
              String name = row.getString("name");
              double price = row.getDouble("price");
              int volume = row.getInt("volume");
              int nr = row.getInt("nr");
              String group = row.getString("product_group");
              Product.Builder builder = new Builder();
              builder.name(name).alcohol(12).nr(nr).type(group).volume(volume).price(price);

              products.add(builder.build());
          //    Log.d(LOG_TAG,
            //      " * " + name + " " + price + " " + volume + " " + nr + " " + group + " ");
            } catch (JSONException e) {
              ; // is ok since this is debug
            }
          }
          Log.d(LOG_TAG, "Sort list");
          Collections.sort(products, Product.PRICE_ORDER);
          for (ProductsChangeListener l : listeners) {
            l.productsChanged();
          }
        }

      }, new Response.ErrorListener() {

    @Override
    public void onErrorResponse(VolleyError error) {
      Log.d(LOG_TAG, " cause: " + error.getCause().getMessage());
    }
  });
// Add the request to the RequestQueue.
    queue.add(jsonArrayRequest);
}

  public void addProductsChangeListener(ProductsChangeListener listener) {
    listeners.add(listener);
  }


}
