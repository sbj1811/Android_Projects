package com.example.android.popularmoviesstage1.Activities;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmoviesstage1.Adapters.MovieViewAdapter;
import com.example.android.popularmoviesstage1.Models.Movies;
import com.example.android.popularmoviesstage1.R;
import com.example.android.popularmoviesstage1.Utils.MovieLoader;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movies>>{

    private static final String TAG = MainActivity.class.getSimpleName();

    private MovieViewAdapter movieViewAdapter;
    private static final String MOVIE_KEY = "thisMovie";
    public static final String POPULAR = "popular";
    public static final String TOP_RATED = "top_rated";
    private static final int LOADER_ID = 1;

    @BindView(R.id.rv_main)
    RecyclerView recyclerView;

    @BindView(R.id.tv_error_message_display)
    TextView emptyTextView;

    @BindView(R.id.pb_loading_indicator)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        movieViewAdapter =  new MovieViewAdapter(new ArrayList<Movies>(), new MovieViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Movies movie) {
 //             Toast.makeText(MainActivity.this, "This is my Toast message!",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                intent.putExtra("thisMovie",movie);
                Log.e(TAG, "CLICK: "+movie);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(movieViewAdapter);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        LoaderManager loaderManager = getLoaderManager();
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            loaderManager.destroyLoader(LOADER_ID);
            loaderManager.initLoader(LOADER_ID, null, MainActivity.this);
        } else {
            recyclerView.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.VISIBLE);
            emptyTextView.setText(R.string.no_internet);
        }


    }

    @Override
    public Loader<List<Movies>> onCreateLoader(int i, Bundle bundle) {
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        String apiKey = this.getString(R.string.apiKey);
        return new MovieLoader(this, POPULAR, apiKey);
    }

    @Override
    public void onLoadFinished(Loader<List<Movies>> loader, List<Movies> movies) {
        progressBar.setVisibility(View.GONE);
        emptyTextView.setText(R.string.no_movies);

        movieViewAdapter.clear();

        if (movies != null && !movies.isEmpty()) {
            recyclerView.setVisibility(View.VISIBLE);
            emptyTextView.setVisibility(View.GONE);
            movieViewAdapter.addAll(movies);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movies>> loader) {
        movieViewAdapter.clear();
    }
}
