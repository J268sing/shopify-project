package com.example.j268sing.shopify;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class collectionDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_details);
        TextView textView = (TextView) findViewById(R.id.ur);
        textView.setText(MainActivity.urlanswer);
        //JSONObject response = new JSONObject(MainActivity.urlanswer);
       // JSONArray count = response.getJSONArray("items");
       // Toast.makeText(getApplicationContext(), "jj", Toast.LENGTH_LONG).show();
    }
}
