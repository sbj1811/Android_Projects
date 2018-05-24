package com.example.android.bakingapp.Networking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sjani on 5/21/2018.
 */

public class ApiConnection {

    public static RecipeEndpointInterface getApi(){

        Gson gson = new GsonBuilder().create();

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RecipeEndpointInterface.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .callFactory(httpClientBuilder.build())
                .build();

        return retrofit.create(RecipeEndpointInterface.class);
    }

}
