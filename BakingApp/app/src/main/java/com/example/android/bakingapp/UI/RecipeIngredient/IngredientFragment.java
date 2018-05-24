package com.example.android.bakingapp.UI.RecipeIngredient;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.Models.Ingredient;
import com.example.android.bakingapp.Models.Recipe;
import com.example.android.bakingapp.Models.Step;
import com.example.android.bakingapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sjani on 5/22/2018.
 */

public class IngredientFragment extends Fragment implements IngredientAdapter.OnStepClickListener {

    private static final String TAG = IngredientFragment.class.getSimpleName();
    private static final String SELECTED_RECIPE = "selected_recipe";
    ArrayList<Recipe> recipes;
    private Recipe recipe;
    private List<Ingredient> ingredients;
    private List<Step> steps;

    @BindView(R.id.tv_ingredient)
    TextView ingredientTextView;

    @BindView(R.id.rv_ingredient)
    RecyclerView ingredientRecyclerView;

    public IngredientFragment() {
    }

    public static IngredientFragment newInstance(Recipe recipe){
        Bundle arguments = new Bundle();
        arguments.putParcelable(SELECTED_RECIPE, recipe);
        IngredientFragment fragment = new IngredientFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        recipes = getArguments().getParcelableArrayList(SELECTED_RECIPE);
        recipe = recipes.get(0);
        ingredients = recipe.getIngredients();
        steps = recipe.getSteps();
        return inflater.inflate(R.layout.ingredient_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        String ingredientListString = extractIngredient(ingredients);
        ingredientTextView.setText(ingredientListString);
        IngredientAdapter adapter = new IngredientAdapter(this,getActivity());
        adapter.getSteps(steps);
        ingredientRecyclerView.setAdapter(adapter);
        ingredientRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private String extractIngredient(List<Ingredient> ingredients){

        StringBuilder sb = new StringBuilder();
        sb.append("Ingredients:");
        Log.e(TAG, "extractIngredient: ingredient: "+ingredients );
        for (int i = 0; i < ingredients.size(); i++) {
            String quantity = "";
            double q = ingredients.get(i).getQuantity();
            String measure = ingredients.get(i).getMeasure();
            String name = ingredients.get(i).getIngredient();
            if (q == (long)q){
                quantity = String.format(Locale.US,"%d",(long)q);
            } else
                quantity = String.format(Locale.US,"%s",q);
            sb.append("\n");
            sb.append((i+1)+") "+name+" ( "+quantity+" "+measure+" )");

        }

        return sb.toString();

    }


    @Override
    public void OnStepClick(Step step) {

    }
}
