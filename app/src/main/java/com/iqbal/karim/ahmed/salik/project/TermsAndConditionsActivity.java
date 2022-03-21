package com.iqbal.karim.ahmed.salik.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import maes.tech.intentanim.CustomIntent;

public class TermsAndConditionsActivity extends AppCompatActivity {

    private String jsonStr;
    private JSONArray items;
    private JSONObject allJSON;
    String hakkimda, iletisim, kvkk;
//    private ArrayList<Item>  items = new ArrayList<>();

    private GestureDetectorCompat mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideTitleBar();
        setContentView(R.layout.activity_terms_and_conditions);

        jsonStr = loadFileFromAssets("tandc.json");
        new JSONReader().execute();

        mDetector = new GestureDetectorCompat(this, new MyGestureListener());
    }

    private void hideTitleBar() {
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "fadein-to-fadeout");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        public boolean onDoubleTap(MotionEvent event) {
            finish();
            return true;
        }
    }

    private class JSONReader extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj;

                    allJSON = new JSONObject(jsonStr);

                    items = allJSON.getJSONArray("tandc");

                    jsonObj = items.getJSONObject(0);
                    hakkimda = jsonObj.getString("hakkimda");

                    jsonObj = items.getJSONObject(1);
                    iletisim = jsonObj.getString("illetisim");

                    jsonObj = items.getJSONObject(2);
                    kvkk = jsonObj.getString("kvkk");

                } catch (JSONException ee) {
                    ee.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            TextView tvHakkimda, tvIletisim, tvKvkk;
            tvHakkimda = findViewById(R.id.hakkimda);
            tvIletisim = findViewById(R.id.illetisim);
            tvKvkk = findViewById(R.id.kvkk);

            tvHakkimda.setText(hakkimda);
            tvIletisim.setText(iletisim);
            tvKvkk.setText(kvkk);
        }
    }

    private String loadFileFromAssets(String fileName) {
        String file = null;
        try {
            InputStream is = getBaseContext().getAssets().open(fileName);

            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();

            file = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return file;
    }
}