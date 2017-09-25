package se.juneday.systemet;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.io.IOException;
import java.util.Properties;
import se.itu.systemet.domain.Product;
import se.juneday.systemet.ProductStore;
import se.juneday.systemet.R;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getName();

    private ArrayAdapter<Product> adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Properties props = System.getProperties();
        String sortimentDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        String sortimentFile= "/Download/sortiment.xml";
        props.setProperty("sortiment-xml-file", sortimentDir + sortimentFile);


        // Lookup ListView
        ListView listView = (ListView) findViewById(R.id.list);

        // Create Adapter
        adapter = new ArrayAdapter<Product>(this,
            android.R.layout.simple_list_item_1,
            ProductStore.getInstance(this).products());

        // Set listView's adapter to the new adapter
        listView.setAdapter(adapter);
    }

    public void onStart() {
        super.onStart();
        Log.d(LOG_TAG, " main act");
       // BolagetParser.printXML2();

    }

}
