package com.example.j268sing.shopify;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONObject;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import android.text.TextUtils;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class collectionDetails extends AppCompatActivity {
    //recycler view initialization
    private RecyclerView recyclerView = null;
    // this variable stores the id that is appended in between the url
    public String urlId;
    RecyclerView.LayoutManager layoutManager;
    //list that stores the productId of products for use in second aysnc task
    public ArrayList<String> productId = new ArrayList<>();
    // this list contains the name of the products that are parsed
    public static ArrayList<String> productName = new ArrayList<>();
    // this list contains the the total available inventory across all variants of the products
    public static  ArrayList<Integer> availableQuantatity = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_details);

        //get the id of the product that has been clicked and store it in urlId for parsing
        urlId =  MainActivity.clickedId;
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //start the async task
        productParsing task = new productParsing();
        task.execute();
    }


    //this class runs on background thread and fetches the productId of the collection that the user clicked on
    // and at the end this class will execute productParsing2 class
    private class productParsing extends AsyncTask<URL, Void, String> {
        private URL createUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException exception) {
                return null;
            }
            return url;
        }
        @Override
        protected String doInBackground(URL... urls) {
            URL url = createUrl("https://shopicruit.myshopify.com/admin/collects.json?collection_id=" +
                    urlId + "&page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6");

            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                // TODO Handle the IOException
            }

            extractFeatureFrom(jsonResponse);
            return jsonResponse;
        }

        @Override
        protected void onPostExecute(String e) {

            productParsing2 task = new productParsing2();
            task.execute();
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

        private ArrayList<String> extractFeatureFrom(String str) {
            ArrayList<String> ans = new ArrayList<>();
            try {
                JSONObject response = new JSONObject(str);
                JSONArray collectionsArray = response.getJSONArray("collects");
                int collectionsCount = collectionsArray.length();

                for (int i = 0; i < collectionsCount; i++) {
                    JSONObject collectionsObject = collectionsArray.getJSONObject(i);
                    String id = "";
                    if (collectionsObject.has("product_id")) {
                        id = collectionsObject.getString("product_id");
                    }
                    productId.add(id);
                }
            } catch (JSONException e) {
            }
            return ans;
        }
    }




    //this class runs on background thread and fethces the name of the products of specific collection and
    //   the quantities of products available
    private class productParsing2 extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            String urlId = TextUtils.join(",", productId);


            URL url = createUrl
                    ("https://shopicruit.myshopify.com/admin/products.json?ids=" + urlId + "&page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6");

            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                // TODO Handle the IOException
            }
            String retval = extractFeatureFrom(jsonResponse);
            return retval;
        }

        @Override
        protected void onPostExecute(String e) {
            updateUi();
        }

        private void updateUi() {
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerAdapter Adapter = new recyclerAdapter();
            recyclerView.setAdapter(Adapter);
            recyclerView.setHasFixedSize(true);
        }

        private URL createUrl(String stringUrl) {
            URL url = null;
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

        private String extractFeatureFrom(String str) {
            try {
                JSONObject Response = new JSONObject(str);
                JSONArray Array = Response.getJSONArray("products");
                int count = Array.length();
                JSONObject productObject;
                productName.clear();
                availableQuantatity.clear();
                for (int i = 0; i < count; i++) {
                    productObject = Array.getJSONObject(i);
                    productName.add(productObject.getString("title"));
                    JSONArray Array2 = productObject.getJSONArray("variants");
                    int quantity = 0;
                    int count2 = Array2.length();
                    JSONObject productObject2;
                    for (int p = 0; p < count2; p++) {
                        productObject2 = Array2.getJSONObject(p);
                        quantity = quantity + productObject2.getInt("inventory_quantity");
                    }
                    availableQuantatity.add(quantity);
                }
            } catch (JSONException e) {
            }
            return urlId;
        }
    }



}
