package com.example.android.popularmovies.Adapters;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;

import com.example.android.popularmovies.Data.MovieContract;
import com.example.android.popularmovies.Models.AccountModel;
import com.example.android.popularmovies.Models.Movies;
import com.example.android.popularmovies.Networking.NetworkUtils;
import com.example.android.popularmovies.R;


import java.util.List;

/**
 * Created by sjani on 5/3/2018.
 */

public class MovieSyncAdapter extends AbstractThreadedSyncAdapter {

    private static final String TAG = MovieSyncAdapter.class.getSimpleName();

    private String apiKey;
    private final ContentResolver contentResolver;
    private List<Movie> moviesPopular;
    private List<Movie> moviesToprated;

    public MovieSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        contentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {

        List<Movies> moviesPopular;
        List<Movies> moviesToprated;
        getFavmovies();

        apiKey = getContext().getResources().getString(R.string.apiKey);
        moviesPopular = NetworkUtils.fetchMovieData("popular", apiKey);
        ContentValues[] popularContent = makeContentFromMoviesList(moviesPopular);
        contentResolver.delete(MovieContract.MovieEntry.CONTENT_URI_POPULAR,null,null);
        contentResolver.bulkInsert(MovieContract.MovieEntry.CONTENT_URI_POPULAR,popularContent);

        moviesToprated = NetworkUtils.fetchMovieData("top_rated", apiKey);
        ContentValues[] topratedContent = makeContentFromMoviesList(moviesToprated);
        contentResolver.delete(MovieContract.MovieEntry.CONTENT_URI_TOP_RATED,null,null);
        contentResolver.bulkInsert(MovieContract.MovieEntry.CONTENT_URI_TOP_RATED,topratedContent);

    }

    private void getFavmovies(){
        List<Movies> movies;
        Cursor cursor;

        cursor = getContext().getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI_FAVORITES,
                null,
                null,
                null, null);

    }

    public static ContentValues[] makeContentFromMoviesList(List<Movies> list) {

        if (list == null) {
            return null;
        }
        ContentValues[] result = new ContentValues[list.size()];

        for (int i = 0; i < list.size(); i++) {
            Movies movie = list.get(i);
            ContentValues movieValues = new ContentValues();
            movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movie.getId());
            movieValues.put(MovieContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
            movieValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
            movieValues.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
            movieValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
            movieValues.put(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH, movie.getBackdropPath());
            movieValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());

            result[i] = movieValues;
        }

        return result;
    }

    public static void performSync() {
        Bundle b = new Bundle();
        b.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        b.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(AccountModel.getAccount(),
                MovieContract.CONTENT_AUTHORITY, b);
    }

}
