package se.juneday.systemet.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import se.juneday.systemet.R;

public class VolleyActivity extends AppCompatActivity {

  private static final String LOG_TAG = VolleyActivity.class.getName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_volley);
  }

  @Override
  protected  void onStart() {
    super.onStart();
    setButtonText("Start");
    ((Button)findViewById(R.id.start_button)).
        setOnClickListener(new OnClickListener() {
                             @Override
                             public void onClick(View view) {
                               download();
                             }
                           }
        );
  }

  private void download() {
    Log.d(LOG_TAG, "Download started");
    setButtonText("Downloading");
  }


  private void setViewText(int id, String s) {
    ((Button)findViewById(id)).setText(s);
  }

  private void setButtonText(String s) {
    setViewText(R.id.start_button, s);
  }

}
