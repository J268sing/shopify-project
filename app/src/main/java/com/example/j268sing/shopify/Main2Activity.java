package com.example.j268sing.shopify;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Main2Activity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    static ArrayList<list_class> myBooksList = new ArrayList<list_class>();
    String url;

    public static String urlanswer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        url = "https://shopicruit.myshopify.com/admin/custom_collections." +
                "json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    Main2Activity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            urlanswer = myResponse;
                        }
                    });
                }
            }
        });



        Main2Activity.googleBookSync googlebooksync = new Main2Activity.googleBookSync();
        googlebooksync.execute();




    }

    void updateui(ArrayList<list_class> sr) {

        myBooksList.add(new list_class("publishedDate",
                "trimmedDetails", "pdfLink", "trimmedpdfname"));
        myBooksList.add(new list_class(
                "publishedDate",
                "trimmedDetails", "pdfLink", "trimmedpdfname"));
        myBooksList.add(new list_class(
                "publishedDate",
                "trimmedDetails", "pdfLink", "trimmedpdfname"));

        myBooksList.add(new list_class(
                "publishedDate",
                "trimmedDetails", "pdfLink", "trimmedpdfname"));
        myBooksList.add(new list_class(
                "publishedDate",
                "trimmedDetails", "pdfLink", "trimmedpdfname"));
        myBooksList.add(new list_class(
                "publishedDate",
                "trimmedDetails", "pdfLink", "trimmedpdfname"));
        myBooksList.add(new list_class(
                "publishedDate",
                "trimmedDetails", "pdfLink", "trimmedpdfname"));

        myBooksList.add(new list_class("trimmedTitle", "trimmedAuthor",
                "pages", "publishedDate"
        ));

        myBooksList.add(new list_class(
                "publishedDate",
                "trimmedDetails", "pdfLink", "trimmedpdfname"));
        myBooksList.add(new list_class(
                "publishedDate",
                "trimmedDetails", "pdfLink", "trimmedpdfname"));
        myBooksList.add(new list_class(
                "publishedDate",
                "trimmedDetails", "pdfLink", "trimmedpdfname"));


        listadapter adapter = new listadapter(this, sr);
        // Get a reference to the ListView, and attach the adapter to the listView.
        final ListView listView = (ListView) findViewById(R.id.list8);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent myIntent = new Intent(view.getContext(), collectionDetails.class);
                startActivity(myIntent);


            }
        });
    }


    void parJsonResponse(String str) {
        try {

            JSONObject response = new JSONObject(str);
            JSONArray collectionsArray = response.getJSONArray("custom_collections");
            int collectionsCount = collectionsArray.length();
            Toast.makeText(getApplicationContext(), Integer.toString(collectionsCount), Toast.LENGTH_LONG).show();

            for (int i = 0; i < collectionsCount; i++) {
                JSONObject collectionsObject = collectionsArray.getJSONObject(i);
                String title = "";
                if (collectionsObject.has("title")) {
                    title = collectionsObject.getString("title");
                }

                String id = collectionsObject.getString("id");
                String image = "";

                if (collectionsObject.has("image")) {

                    JSONObject imageObject = collectionsObject.getJSONObject("image");
                    if (imageObject.has("src")) {
                        image = imageObject.getString("srs");
                    }
                }

                myBooksList.add(new list_class(title, id, image, ""));


            }


        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the result", e);
        }
    }


    private class googleBookSync extends AsyncTask<String, Void, ArrayList<list_class>> {
        @Override
        protected ArrayList<list_class> doInBackground(String... str) {




            ArrayList<list_class> ans = extractFeatureFrom(urlanswer);
            return ans;
        }

        @Override
        protected void onPostExecute(ArrayList<list_class> e) {
            updateui(e);
        }


        private ArrayList<list_class> extractFeatureFrom(String str) {
                try {

                    JSONObject response = new JSONObject(str);
                 /*   JSONArray collectionsArray = response.getJSONArray("custom_collections");
                    int collectionsCount = collectionsArray.length();
                    Toast.makeText(getApplicationContext(), Integer.toString(collectionsCount), Toast.LENGTH_LONG).show();

                    for (int i = 0; i < collectionsCount; i++) {
                        JSONObject collectionsObject = collectionsArray.getJSONObject(i);
                        String title = "";
                        if (collectionsObject.has("title")) {
                            title = collectionsObject.getString("title");
                        }

                        String id = collectionsObject.getString("id");
                        String image = "";

                        if (collectionsObject.has("image")) {

                            JSONObject imageObject = collectionsObject.getJSONObject("image");
                            if (imageObject.has("src")) {
                                image = imageObject.getString("srs");
                            }
                        }

                        myBooksList.add(new list_class(title, id, image, ""));



                    }
*/

                } catch (JSONException e) {
                    Log.e(LOG_TAG, "Problem parsing the result", e);
                }
            return myBooksList;



        }
    }






}
