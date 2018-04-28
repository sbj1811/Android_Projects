package com.example.android.popularmovies.Utils;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.android.popularmovies.Models.Movies;
import com.example.android.popularmovies.Networking.NetworkUtils;

import java.util.List;

/**
 * Created by sjani on 3/30/2018.
 */

public class MovieLoader extends AsyncTaskLoader<List<Movies>> {

    private static final String TAG = MovieLoader.class.getSimpleName();

    private String sortOrder;
    private String apiKey;

    public MovieLoader(Context context, String mSortOrder,String mApiKey) {
        super(context);
        sortOrder = mSortOrder;
        apiKey = mApiKey;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Movies> loadInBackground() {

        Log.e(TAG, "SORTORDER: "+sortOrder );
        List<Movies> movies = NetworkUtils.fetchMovieData(sortOrder, apiKey);
        return movies;
    }
}
