package com.example.android.booklisting;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private BookAdapter mAdapter;

    private TextView emptyTextView;

    private EditText searchStringView;

    private Button searchButton;

    private String API_URL = "";

    private static final String SEARCH_API_STRING = "https://www.googleapis.com/books/v1/volumes?q=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(LOG_TAG, "START : onCreate");
        ListView bookListview = (ListView) findViewById(R.id.list);

        mAdapter = new BookAdapter(this, new ArrayList<Book>());

        bookListview.setAdapter(mAdapter);

        emptyTextView = (TextView) findViewById(R.id.empty_view);
        bookListview.setEmptyView(emptyTextView);

        bookListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Book book = mAdapter.getItem(i);
                Uri bookUrl = Uri.parse(book.getBookUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUrl);
                startActivity(websiteIntent);
            }
        });

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        searchButton = (Button)findViewById(R.id.search_button);
        searchStringView = (EditText) findViewById(R.id.search_view);
        //bookAsyncTask task = new bookAsyncTask();
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                    API_URL = SEARCH_API_STRING + searchStringView.getText().toString();
                    new bookAsyncTask().execute(API_URL);
                } else {
                    ProgressBar progressBar = (ProgressBar) findViewById(R.id.loading_spinner);
                    progressBar.setVisibility(View.GONE);
                    emptyTextView.setText(R.string.no_internet);
                }
            }
        });

    }

    private class bookAsyncTask extends AsyncTask<String, Void, List<Book>> {

        @Override
        protected List<Book> doInBackground(String... urls) {
            Log.e(LOG_TAG, "START : doInBackground");
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<Book> result = QueryUtils.fetchBookData(urls[0]);
            return result;

        }

        @Override
        protected void onPostExecute(List<Book> books) {
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.loading_spinner);
            progressBar.setVisibility(View.GONE);
            emptyTextView.setText(R.string.no_books);

            Log.e(LOG_TAG, "START : onPostExecute");
            mAdapter.clear();

            if (books != null && !books.isEmpty()) {
                mAdapter.addAll(books);
            }
        }
    }


}
