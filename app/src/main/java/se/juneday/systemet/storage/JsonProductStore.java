package se.juneday.systemet.storage;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import se.juneday.systemet.storage.ObjectCache.CACHE_TYPE;
import se.juneday.systemet.utils.NetworkChecker;
import se.juneday.systemet.utils.NetworkChecker.NetworkCheckerListener;


public class JsonProductStore implements ProductStore {

  private static final String LOG_TAG = JsonProductStore.class.getName() ;
  private static JsonProductStore store;
  private List<Product> products;
  private Context context;
  private ObjectCache<Product> cache;
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
//    products = new ArrayList<>();
    listeners = new ArrayList<>();
    cache = new ObjectCache(Product.class, context, CACHE_TYPE.LIST);
    cache.pull();
    products = cache.get();

    if (products==null) {
      products = new ArrayList<>();
    }
    if (products!=null) {    Log.d(LOG_TAG, " products in store: " + products.size()); }


    NetworkChecker nwChecker = NetworkChecker.getInstance(context);
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
    context.registerReceiver(nwChecker, intentFilter);
    nwChecker .setNetworkCheckerListener(new NetworkCheckerListener() {
      @Override
      public void networkChange(boolean connected, NetworkInfo info) {
        Log.d(LOG_TAG, "networkChange: " + connected);
        if (connected) {
          syncProducts();
        }
      }
    });
  }


  public void syncProducts() {
    Log.d(LOG_TAG, "Sync products");
    List<Product> tmpProducts = new ArrayList<>();

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
              double alcohol = row.getDouble("alcohol");
              int volume = row.getInt("volume");
              int nr = row.getInt("nr");
              String group = row.getString("product_group");
              Product.Builder builder = new Builder();
              builder.name(name).alcohol(alcohol).nr(nr).type(group).volume(volume).price(price);
              tmpProducts.add(builder.build());
          //    Log.d(LOG_TAG,
            //      " * " + name + " " + price + " " + volume + " " + nr + " " + group + " ");
            } catch (JSONException e) {
              ; // is ok since this is debug
            }
          }
          Log.d(LOG_TAG, "Sort list, items: " + tmpProducts.size());
          Collections.sort(tmpProducts, Product.PRICE_ORDER);
          cache.set(products);
          for (ProductsChangeListener l : listeners) {
            l.productsChanged();
          }
          products = tmpProducts;
          /*
          for (Product p : products) {
            Log.d(LOG_TAG, " * " + p.price() + " " + p.name());
          }
          */
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

  @Override
  public void close() {
    Log.d(LOG_TAG, "close()");
    cache.set(products);
    cache.commit();
  }

  public void addProductsChangeListener(ProductsChangeListener listener) {
    listeners.add(listener);
  }

}
