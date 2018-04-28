package com.example.android.popularmovies.Networking;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sjani on 3/31/2018.
 */

public class MovieDbApiConnection {

    private static final String TAG = MovieDbApiConnection.class.getSimpleName();
    /**
     * Sends out network requests to MovieDB API
     * @return
     */

    public static com.example.android.popularmovies.Networking.MovieDbApiEndpointInterface getApi () {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(com.example.android.popularmovies.Networking.MovieDbApiEndpointInterface.API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(com.example.android.popularmovies.Networking.MovieDbApiEndpointInterface.class);
    }
}
