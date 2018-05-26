package com.example.android.bakingapp.Utils;

import com.example.android.bakingapp.Models.Recipe;
import com.example.android.bakingapp.Models.Step;

import java.util.List;

/**
 * Created by sjani on 5/24/2018.
 */

public interface stepItemClickListener {
    void onStepItemClick(List<Step> steps, int index, Recipe recipe);
}
