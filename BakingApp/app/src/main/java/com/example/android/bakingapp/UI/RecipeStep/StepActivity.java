package com.example.android.bakingapp.UI.RecipeStep;

import android.graphics.Rect;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

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


    @BindView(R.id.step_number)
    TextView mStepNum;
    @BindView(R.id.prev_step)
    ImageButton mPrevButton;
    @BindView(R.id.next_step)
    ImageButton mNextButton;


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
        updateStepNumberText(selectecStepIndex);

        if (mPrevButton != null) {
            mPrevButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectecStepIndex--;
                    stepFragment = StepFragment.newInstance(steps,selectecStepIndex);
                    updateStepNumberText(selectecStepIndex);
                    getSupportFragmentManager().beginTransaction().replace(R.id.step_container,stepFragment,TAG_RETAINED_FRAGMENT).commit();
                }
            });
        }

        if (mNextButton != null) {
            mNextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectecStepIndex++;
                    stepFragment = StepFragment.newInstance(steps,selectecStepIndex);
                    updateStepNumberText(selectecStepIndex);
                    getSupportFragmentManager().beginTransaction().replace(R.id.step_container,stepFragment,TAG_RETAINED_FRAGMENT).commit();
                }
            });
        }

        if(stepFragment == null) {
            stepFragment = StepFragment.newInstance(steps,selectecStepIndex);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_container, stepFragment,TAG_RETAINED_FRAGMENT)
                    .commit();
        }


    }

    private void updateStepNumberText(int stepPosition){

        if(mStepNum != null) {
            mStepNum.setText(Integer.toString(stepPosition));

            // Check position and hide arrow if at 0 or last position,
            // preventing going negative and higher than list size
            if (stepPosition == 0) {
                mPrevButton.setVisibility(View.GONE);
            } else if (stepPosition == steps.size() - 1) {
                mNextButton.setVisibility(View.GONE);
            } else {
                mPrevButton.setVisibility(View.VISIBLE);
                mNextButton.setVisibility(View.VISIBLE);
            }
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
