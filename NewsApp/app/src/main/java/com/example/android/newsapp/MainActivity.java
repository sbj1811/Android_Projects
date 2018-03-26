package com.example.android.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,LoaderManager.LoaderCallbacks<List<News>> {

    public static final String LOG_TAG = MainActivity.class.getName();

    private static final int NEWS_LOADER_ID = 1;

    private static final String NEWS_REQUEST_URL = "http://content.guardianapis.com/search";

    private String API_URL = "";

    private String searchQuery;

    private TextView emptyTextView;

    private NewsAdapter mAdapter;

    private EditText searchEditText;

    private Button searchButton;

    private RecyclerView mRecyclerView;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        emptyTextView = (TextView) findViewById(R.id.empty_view);

        progressBar = (ProgressBar) findViewById(R.id.loading_spinner);

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        mAdapter = new NewsAdapter(new ArrayList<News>(), new NewsAdapter.OnItemClickListener() {
            @Override
            public void onClick(News news) {
                Log.e(LOG_TAG, "onClick: INTENT");
                Uri newsUri = Uri.parse(news.getNewsUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                startActivity(websiteIntent);
            }
        });

        mRecyclerView.setAdapter(mAdapter);


        //mRecyclerView.setEmptyView(emptyTextView);

//        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                News news = mAdapter.getItem(i);
//                Uri newsUri = Uri.parse(news.getNewsUrl());
//                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
//                startActivity(websiteIntent);
//            }
//        });

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        LoaderManager loaderManager = getLoaderManager();
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            loaderManager.destroyLoader(NEWS_LOADER_ID);
            loaderManager.initLoader(NEWS_LOADER_ID, null, MainActivity.this);
            Log.e(LOG_TAG, "onClick: BUTTON CLICKED!!");
        } else {
            mRecyclerView.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.VISIBLE);
            emptyTextView.setText(R.string.no_internet);
        }



//        searchButton = (Button) findViewById(R.id.search_button);
//        searchEditText = (EditText) findViewById(R.id.search_view);
//        final LoaderManager loaderManager = getLoaderManager();
//        searchButton.setFocusable(false);
//        searchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
//                    loaderManager.destroyLoader(NEWS_LOADER_ID);
//                    loaderManager.initLoader(NEWS_LOADER_ID, null, MainActivity.this);
//                    Log.e(LOG_TAG, "onClick: BUTTON CLICKED!!");
//                } else {
//                    progressBar = (ProgressBar) findViewById(R.id.loading_spinner);
//                    mRecyclerView.setVisibility(View.GONE);
//                    progressBar.setVisibility(View.GONE);
//                    emptyTextView.setVisibility(View.VISIBLE);
//                    emptyTextView.setText(R.string.no_internet);
//                }
//            }
//        });

    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        mRecyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String max_article = sharedPreferences.getString(getString(R.string.settings_max_articles_key), getString(R.string.settings_max_articles_default));
        Uri baseUri = Uri.parse(NEWS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("page-size", max_article);
        uriBuilder.appendQueryParameter("show-fields", "thumbnail");
        uriBuilder.appendQueryParameter("api-key", "test");
        uriBuilder.appendQueryParameter("q", searchQuery);
        Log.e(LOG_TAG, "onCreateLoader: "+ uriBuilder.toString());
        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.loading_spinner);
        progressBar.setVisibility(View.GONE);
        emptyTextView.setText(R.string.no_news);

        mAdapter.clear();

        if (news != null && !news.isEmpty()) {
            mRecyclerView.setVisibility(View.VISIBLE);
            emptyTextView.setVisibility(View.GONE);
            mAdapter.addAll(news);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mAdapter.clear();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        searchQuery = s;
        getLoaderManager().restartLoader(NEWS_LOADER_ID, null, this);
        return true;
    }
}
