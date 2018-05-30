package com.example.android.bakingapp.UI.RecipeIngredient;

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
import android.widget.TextView;

import com.example.android.bakingapp.Models.Ingredient;
import com.example.android.bakingapp.Models.Recipe;
import com.example.android.bakingapp.Models.Step;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.Utils.Utility;
import com.example.android.bakingapp.Utils.stepItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by sjani on 5/22/2018.
 */

public class IngredientFragment extends Fragment {

    private static final String TAG = IngredientFragment.class.getSimpleName();
    private static final String SELECTED_RECIPE = "selected_recipe";
    private Recipe recipe;
    private List<Ingredient> ingredients;
    private stepItemClickListener listener;
    private Unbinder unbinder;

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
        recipe = getArguments().getParcelable(SELECTED_RECIPE);
        ingredients = recipe.getIngredients();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ingredient_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this,view);
        String ingredientListString = extractIngredient(ingredients);
        ingredientTextView.setText(ingredientListString);
        listener = (IngredientActivity)getActivity();
        IngredientAdapter adapter = new IngredientAdapter(listener,getContext());
        adapter.getRecipe(recipe);
        ingredientRecyclerView.setAdapter(adapter);
        ingredientRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private String extractIngredient(List<Ingredient> ingredients){

        StringBuilder sb = new StringBuilder();
        sb.append("Ingredients:");
        for (int i = 0; i < ingredients.size(); i++) {
            String quantity = "";
            double q = ingredients.get(i).getQuantity();
            String measure = ingredients.get(i).getMeasure();
            String name = ingredients.get(i).getIngredient();
            quantity = Utility.formatQuantity(q);
            sb.append("\n");
            sb.append((i+1)+") "+name+" ( "+quantity+" "+measure.toLowerCase()+" )");

        }

        return sb.toString();

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SELECTED_RECIPE,recipe);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
