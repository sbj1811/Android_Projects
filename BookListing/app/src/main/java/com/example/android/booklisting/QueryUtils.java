package com.example.android.booklisting;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarException;

/**
 * Created by sjani on 2/20/2018.
 */

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    public static List<Book> fetchBookData(String requestUrl){
        Log.e(LOG_TAG, "START : fetchBookData");
        URL url = createURL(requestUrl);

        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        } catch (IOException io){
            Log.e(LOG_TAG, "Problem making the HTTP request.", io);
        }

        List<Book> books = extractDatafromJson(jsonResponse);

        return books;

    }

    private static URL createURL(String requestUrl){
        Log.e(LOG_TAG, "START : createURL");
        URL url = null;
        try{
            url = new URL(requestUrl);
        } catch (MalformedURLException u){
            Log.e(LOG_TAG, "Problem building the URL ", u);
        }

        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        Log.e(LOG_TAG, "START : makeHttpRequest");
        String jsonResponse = "";

        if (url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else{
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException io){
            Log.e(LOG_TAG, "Problem retrieving the Book JSON results.", io);
        } finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;

    }


    private static String readFromStream(InputStream inputStream) throws IOException {
        Log.e(LOG_TAG, "START : readFromStream");
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

    private static List<Book> extractDatafromJson(String bookJson){
        Log.e(LOG_TAG, "START : extractDatafromJson");
        if (TextUtils.isEmpty(bookJson)) {
            return null;
        }

        List<Book> books = new ArrayList<>();
        String title = "";
        String author = "";
        String publisher = "";
        String imageLinks = "";
        String price = "";
        String url = "";

        try {

            JSONObject root = new JSONObject(bookJson);
            JSONArray items = root.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                JSONObject jsonObject = items.getJSONObject(i);
                JSONObject volumeInfo = jsonObject.getJSONObject("volumeInfo");
                JSONObject saleInfo = jsonObject.getJSONObject("saleInfo");
                String saleability = saleInfo.getString("saleability");
                title = volumeInfo.getString("title");
                JSONArray authorArray = volumeInfo.getJSONArray("authors");
                for (int j = 0; j < authorArray.length(); j++) {
                    author = authorArray.getString(j);
                    if (authorArray.length() > 1 ) {
                        author = author + ", ";
                    }
                }
                publisher = volumeInfo.getString("publisher");
                JSONObject imageLinksObj = volumeInfo.getJSONObject("imageLinks");
                imageLinks = imageLinksObj.getString("smallThumbnail");
                url = volumeInfo.getString("infoLink");
                Log.e(LOG_TAG, "START : I am here "+saleability);
                if (saleability.equals("NOT_FOR_SALE")){
                    price = "Not Available in US";
                } else if (saleability.equals("FOR_SALE")) {
                    JSONObject retailPrice = saleInfo.getJSONObject("retailPrice");
                    Double amount = retailPrice.getDouble("amount");
                    price = "$"+String.valueOf(amount);
                }

                Book book = new Book(title, author, publisher, price, imageLinks, url);
                books.add(book);
            }

        } catch (JSONException j){
            Log.e(LOG_TAG, "Problem parsing the Google Books API JSON results", j);
        }

        return books;

    }

}
