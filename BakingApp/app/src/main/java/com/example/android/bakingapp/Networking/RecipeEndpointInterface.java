package com.example.android.bakingapp.Networking;

import com.example.android.bakingapp.Models.Recipe;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by sjani on 5/21/2018.
 */

public interface RecipeEndpointInterface {

    String ENDPOINT = "https://d17h27t6h515a5.cloudfront.net";

    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<ArrayList<Recipe>> loadRecipe();

}
