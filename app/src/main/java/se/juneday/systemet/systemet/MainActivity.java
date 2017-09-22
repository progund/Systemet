package se.juneday.systemet.systemet;

import static android.os.Environment.getDownloadCacheDirectory;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import java.util.List;
import java.util.Properties;
import se.itu.systemet.storage.ProductLineFactory;
import se.itu.systemet.storage.ProductLine;
import se.itu.systemet.domain.Product;


public class MainActivity extends AppCompatActivity {

  private static final String LOG_TAG = MainActivity.class.getName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  protected void onStart() {
    super.onStart();

    Properties props = System.getProperties();
    String sortimentPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/sortiment.xml";
    props.setProperty("sortiment-xml-file", sortimentPath);

    Log.d(LOG_TAG, "file: " + sortimentPath);
    Log.d(LOG_TAG, "file: " + props.getProperty("sortiment-xml-file"));

    // Get the ProductLine:
    ProductLine productLine = ProductLineFactory.getProductLine();
    // Get all Products:
    List<Product> products = productLine.getAllProducts();

    // Loop through all products:
    for (Product product : products) {
      // Increment count
     Log.d(LOG_TAG, " * " + product);
    }
  }

}
