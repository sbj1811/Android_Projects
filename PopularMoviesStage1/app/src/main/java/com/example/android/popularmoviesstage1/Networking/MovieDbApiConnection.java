package com.example.android.popularmoviesstage1.Networking;

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

    public static MovieDbApiEndpointInterface getApi () {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MovieDbApiEndpointInterface.API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(MovieDbApiEndpointInterface.class);
    }
}
