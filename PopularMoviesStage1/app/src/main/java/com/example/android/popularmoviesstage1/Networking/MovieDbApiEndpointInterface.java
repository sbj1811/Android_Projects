package com.example.android.popularmoviesstage1.Networking;

import com.example.android.popularmoviesstage1.Models.MovieList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by sjani on 3/31/2018.
 */

public interface MovieDbApiEndpointInterface {
    /**
     * Sends GET request to /3/movie/popular or /3/movie/top_rated
     *
     * @param sortingKey Sorting criteria (popular or top_rated)
     * @param apiKey     MovieDB API key
     * @return MoviesList which contains a list of Movie objects.
     */
    String API_ENDPOINT = "http://api.themoviedb.org/";

    @GET("/3/movie/{sorting}")
    Call<MovieList> getMovies(@Path("sorting") String sortingKey, @Query("api_key") String apiKey);

}