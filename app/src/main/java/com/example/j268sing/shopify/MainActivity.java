package com.example.j268sing.shopify;


import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URL;
import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.nio.charset.Charset;


public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    static ArrayList<list_class> myBooksList = new ArrayList<list_class>();
    static String clickedImage;
    static String clickedTitle;
    static String clickedId;
    public static String urlanswer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        collectionsResponse task = new collectionsResponse();
        task.execute();


    }

    void  updateui(ArrayList<list_class> j) {

        listadapter adapter = new listadapter(this, j);
        // Get a reference to the ListView, and attach the adapter to the listView.
        final ListView listView = (ListView) findViewById(R.id.list8);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                clickedImage = myBooksList.get(position).getmImage();
                clickedTitle = myBooksList.get(position).getmTitle();
                clickedId = myBooksList.get(position).getmId();
                Intent myIntent = new Intent(view.getContext(), collectionDetails.class);
                startActivity(myIntent);
            }
        });
    }

    private class collectionsResponse extends AsyncTask<URL, Void, ArrayList<list_class>> {
        @Override
        protected ArrayList<list_class> doInBackground(URL... urls) {
            URL url = createUrl("https://shopicruit.myshopify.com/admin/custom_collections.json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6");

            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {

            }
             ArrayList<list_class> ans = extractFeatureFrom(jsonResponse);
            return ans;
        }

        @Override
        protected void onPostExecute(ArrayList<list_class> e) {
            updateui(e);
        }

        private URL createUrl(String stringUrl) {
            URL url =  null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException exception) {
                return null;
            }
            return url;
        }

        private String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.connect();
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } catch (IOException e) {
                // TODO: Handle the exception
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            return jsonResponse;
        }


        private String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }

        private ArrayList<list_class> extractFeatureFrom(String anss) {
            try {
                JSONObject response = new JSONObject(anss);
                JSONArray collectionsArray = response.getJSONArray("custom_collections");
                int collectionsCount = collectionsArray.length();

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
                            image = imageObject.getString("src");
                        }
                    }
                    myBooksList.add(new list_class(title, id, image));
                }
                return myBooksList;
            } catch (JSONException e) {

                Log.e(LOG_TAG, "Problem parsing the result", e);
            }
            return myBooksList;
        }
    }
}