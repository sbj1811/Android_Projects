package com.example.android.bakingapp.UI.RecipeList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.android.bakingapp.Models.Recipe;
import com.example.android.bakingapp.Networking.ApiConnection;
import com.example.android.bakingapp.Networking.RecipeEndpointInterface;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.UI.RecipeIngredient.IngredientActivity;
import com.example.android.bakingapp.Utils.ItemClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sjani on 5/21/2018.
 */

public class ListFragment extends Fragment implements ItemClickListener {

    private static final String TAG = ListFragment.class.getSimpleName();
    private static final String SELECTED_RECIPE = "selected_recipe";

    private Unbinder unbinder;


    @BindView(R.id.rv_main)
    RecyclerView recyclerView;
    @BindView(R.id.pb_loading_indicator)
    ProgressBar progressBar;



    public ListFragment() {
    }

    public static ListFragment newInstance(){
        return new ListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this,view);
        final ListRecipeAdapter adapter = new ListRecipeAdapter(this,getActivity());
        RecipeEndpointInterface recipeEndpointInterface =  ApiConnection.getApi();
        Call<ArrayList<Recipe>> recipes = recipeEndpointInterface.loadRecipe();
        recipes.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                ArrayList<Recipe> recipes = response.body();
                adapter.setRecipes(recipes);
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {

            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onItemClick(Recipe selectedRecipe) {
        Bundle bundle = new Bundle();
        ArrayList<Recipe> selectedRecipeArray = new ArrayList<>();
        selectedRecipeArray.add(selectedRecipe);
        bundle.putParcelableArrayList(SELECTED_RECIPE,selectedRecipeArray);
        Intent intent = new Intent(getActivity(), IngredientActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent,1);
    }
}
