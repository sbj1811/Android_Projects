package com.example.android.bakingapp.UI.RecipeIngredient;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.android.bakingapp.Data.IngredientsColumns;
import com.example.android.bakingapp.Data.RecipeColumns;
import com.example.android.bakingapp.Data.RecipeContentProvider;
import com.example.android.bakingapp.Data.RecipeDb;
import com.example.android.bakingapp.Models.Ingredient;
import com.example.android.bakingapp.Models.Recipe;
import com.example.android.bakingapp.Models.Step;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.UI.RecipeStep.StepActivity;
import com.example.android.bakingapp.UI.RecipeStep.StepFragment;
import com.example.android.bakingapp.UI.Widget.RecipeWidgetService;
import com.example.android.bakingapp.Utils.Utility;
import com.example.android.bakingapp.Utils.stepItemClickListener;
import com.google.android.exoplayer2.C;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientActivity extends AppCompatActivity implements stepItemClickListener {

    private static final String TAG = IngredientActivity.class.getSimpleName();
    private static final String SELECTED_RECIPE = "selected_recipe";
    private static final String SELECTED_STEP = "selected_step";
    private static final String INDEX = "index";
    private static final String POSITION = "position";
    public static final String UPDATE_WIDGET = "update_widget";

    boolean mDualPane;

    @BindView(R.id.ingredient_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.ingredient_toolbarText)
    TextView mToolbarText;
    @BindView(R.id.fav_button)
    ToggleButton favButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = this.getIntent().getExtras();
        final ArrayList<Recipe> recipes = bundle.getParcelableArrayList(SELECTED_RECIPE);
        final int position = bundle.getInt(POSITION);
        final Recipe recipe = recipes.get(0);
        final ArrayList<Step> steps = (ArrayList<Step>) recipe.getSteps();
        mToolbarText.setText(recipe.getName());
        if (savedInstanceState == null) {
            IngredientFragment ingredientFragment = IngredientFragment.newInstance(recipe);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.ingredient_container, ingredientFragment)
                    .commit();
        } else {

        }

        final Intent update = new Intent(UPDATE_WIDGET);

        if(Utility.checkRecipeExist(this,recipe.getId())){
            favButton.setChecked(true);
            IngredientActivity.this.sendBroadcast(update);
        } else {
            favButton.setChecked(false);
        }


        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(favButton.isChecked()){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            ContentValues values = new ContentValues();
                            values.put(RecipeColumns.RECIPE_NAME,recipe.getName());
                            values.put(RecipeColumns.RECIPE_ID,recipe.getId());
                            values.put(RecipeColumns.SERVING_SIZE,recipe.getServings());
                            Utility.setIngredientName(IngredientActivity.this,recipe.getName());
                            ContentValues[] ingredientContent = Utility.makeIngredientsList(recipe.getIngredients(),recipe);
                            getContentResolver().delete(RecipeContentProvider.RecipeList.CONTENT_URI,null,null);
                            getContentResolver().delete(RecipeContentProvider.RecipeIngredients.CONTENT_URI,null,null);
                            getContentResolver().insert(RecipeContentProvider.RecipeList.CONTENT_URI, values);
                            getContentResolver().bulkInsert(RecipeContentProvider.RecipeIngredients.CONTENT_URI,ingredientContent);
                            RecipeWidgetService.startActionUpdateWidgets(IngredientActivity.this);

                //            favButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_favorite));
                        }
                    }).start();
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Utility.setIngredientName(IngredientActivity.this,"");
                            getContentResolver().delete(RecipeContentProvider.RecipeList.CONTENT_URI,null,null);
                            getContentResolver().delete(RecipeContentProvider.RecipeIngredients.CONTENT_URI,null,null);
                            RecipeWidgetService.startActionUpdateWidgets(IngredientActivity.this);
                 //           favButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_favorite_border));
                        }
                    }).start();
                }

            }
        });

        if(findViewById(R.id.ingredient_step_container) != null){
            mDualPane = true;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.ingredient_step_container, StepFragment.newInstance((ArrayList<Step>) steps,position))
                    .commit();
        } else {
            mDualPane = false;
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
                    .replace(R.id.ingredient_step_container, StepFragment.newInstance((ArrayList<Step>) steps,index))
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
