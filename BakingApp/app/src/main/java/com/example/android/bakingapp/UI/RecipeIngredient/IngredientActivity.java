package com.example.android.bakingapp.UI.RecipeIngredient;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.example.android.bakingapp.Models.Ingredient;
import com.example.android.bakingapp.Models.Recipe;
import com.example.android.bakingapp.Models.Step;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.UI.RecipeStep.StepActivity;
import com.example.android.bakingapp.UI.RecipeStep.StepFragment;
import com.example.android.bakingapp.Utils.stepItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientActivity extends AppCompatActivity implements stepItemClickListener {

    private static final String TAG = IngredientActivity.class.getSimpleName();
    private static final String SELECTED_RECIPE = "selected_recipe";
    private static final String SELECTED_STEP = "selected_step";
    private static final String INDEX = "index";

    boolean mDualPane;

    @BindView(R.id.ingredient_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.ingredient_toolbarText)
    TextView mToolbarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = this.getIntent().getExtras();
        final ArrayList<Recipe> recipes = bundle.getParcelableArrayList(SELECTED_RECIPE);
        final Recipe recipe = recipes.get(0);
        mToolbarText.setText(recipe.getName());
        if (savedInstanceState == null) {
            IngredientFragment ingredientFragment = IngredientFragment.newInstance(recipe);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.ingredient_container, ingredientFragment)
                    .commit();
        } else {

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.e(TAG, "onBackPressed: HERE");
    }


    @Override
    public void onStepItemClick(List<Step> steps, int index, Recipe recipe) {
        if (mDualPane){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.ingredient_container, StepFragment.newInstance((ArrayList<Step>) steps,index))
                    .commit();

        } else {
            ArrayList<Recipe> recipes = new ArrayList<Recipe>();
            recipes.add(recipe);
            Intent intent = new Intent(this, StepActivity.class)
                    .putParcelableArrayListExtra(SELECTED_STEP,(ArrayList<Step>) steps)
                    .putParcelableArrayListExtra(SELECTED_RECIPE,recipes)
                    .putExtra(INDEX,index);
            startActivity(intent);
        }
    }


}
