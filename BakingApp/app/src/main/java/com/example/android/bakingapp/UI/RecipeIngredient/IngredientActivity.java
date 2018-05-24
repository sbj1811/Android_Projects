package com.example.android.bakingapp.UI.RecipeIngredient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.bakingapp.Models.Ingredient;
import com.example.android.bakingapp.Models.Recipe;
import com.example.android.bakingapp.R;

import java.util.ArrayList;
import java.util.List;

public class IngredientActivity extends AppCompatActivity {

    private static final String TAG = IngredientActivity.class.getSimpleName();
    private static final String SELECTED_RECIPE = "selected_recipe";
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        Bundle bundle = getIntent().getExtras();
        ArrayList<Recipe> recipes = bundle.getParcelableArrayList(SELECTED_RECIPE);
        recipe = recipes.get(0);

        IngredientFragment ingredientFragment = IngredientFragment.newInstance(recipe);
        ingredientFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.ingredient_container,ingredientFragment)
                .commit();

    }
}
