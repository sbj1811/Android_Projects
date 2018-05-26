package com.example.android.bakingapp.UI.RecipeStep;

import android.graphics.Rect;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.android.bakingapp.Models.Recipe;
import com.example.android.bakingapp.Models.Step;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.UI.RecipeIngredient.IngredientFragment;
import com.example.android.bakingapp.UI.RecipeList.MainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepActivity extends AppCompatActivity {

    private static final String TAG = StepActivity.class.getSimpleName();

    private static final String SELECTED_STEP = "selected_step";
    private static final String SELECTED_RECIPE = "selected_recipe";
    private static final String INDEX = "index";
    private static final String STEP_INDEX = "step_index";
    private static final String TAG_RETAINED_FRAGMENT = "RetainedFragment";


    @BindView(R.id.main_coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;


    private ArrayList<Step> steps;
    private int selectecStepIndex;
    private Step step;
    private Recipe recipe;
    private StepFragment stepFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        steps = bundle.getParcelableArrayList(SELECTED_STEP);
        ArrayList<Recipe> recipes = bundle.getParcelableArrayList(SELECTED_RECIPE);
        selectecStepIndex = bundle.getInt(INDEX);
        step = steps.get(selectecStepIndex);
        recipe = recipes.get(0);

        if(savedInstanceState == null) {
            selectecStepIndex = bundle.getInt(INDEX);
        } else {
            selectecStepIndex = savedInstanceState.getInt(STEP_INDEX);
        }

        stepFragment = (StepFragment) getSupportFragmentManager().findFragmentByTag(TAG_RETAINED_FRAGMENT);

        if(stepFragment == null) {
            stepFragment = StepFragment.newInstance(steps,selectecStepIndex);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_container, stepFragment,TAG_RETAINED_FRAGMENT)
                    .commit();
        }


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.e(TAG, "onBackPressed: HERE");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STEP_INDEX,selectecStepIndex);
    }
}
