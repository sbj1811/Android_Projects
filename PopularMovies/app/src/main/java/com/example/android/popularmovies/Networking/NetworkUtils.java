package com.example.android.popularmovies.Networking;

import android.util.Log;

import com.example.android.popularmovies.Networking.MovieDbApiConnection;
import com.example.android.popularmovies.Models.MovieList;
import com.example.android.popularmovies.Models.Movies;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by sjani on 3/30/2018.
 */

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    public static List<Movies> fetchMovieData (String sortOrder, String apiKey){

        List<Movies> movies = null;

        try {
            movies = loadMovies(sortOrder,apiKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movies;
    }

    private static List<Movies> loadMovies (String sortOrder, String apiKey) throws IOException {
        Call<MovieList> movies = MovieDbApiConnection.getApi().getMovies(sortOrder,apiKey);
        Response<MovieList> response = movies.execute();
        Log.e(TAG, "loadMovies: "+response);
        List<Movies> M = response.body().getResults();
        return M;
    }


}
