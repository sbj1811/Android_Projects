package com.example.android.bakingapp.UI.RecipeIngredient;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.Models.Recipe;
import com.example.android.bakingapp.Models.Step;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.Utils.stepItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sjani on 5/22/2018.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientAdapterViewHolder> {

    private static final String TAG = IngredientAdapter.class.getSimpleName();

    private Context context;
    private stepItemClickListener listener;
    private List<Step> steps;
    private Recipe recipe;

    public IngredientAdapter(stepItemClickListener listener,Context context) {
        this.context = context;
        this.listener = listener;
    }

    public void getRecipe(Recipe mRecipe){
        this.recipe = mRecipe;
        this.steps = mRecipe.getSteps();
    }


    @Override
    public IngredientAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ingredient_item,parent,false);
        IngredientAdapterViewHolder holder = new IngredientAdapterViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(IngredientAdapterViewHolder holder, int position) {
            String stepDescription = steps.get(position).getShortDescription();
            holder.tvStepDescription.setText(position+") "+stepDescription);
            if (steps.get(position).getVideoURL().equals("")){
                holder.playIcon.setVisibility(View.GONE);
            }
    }


    @Override
    public int getItemCount() {
        if (steps == null){
            return 0;
        }
        return steps.size();
    }

    public class IngredientAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_step_description)
        TextView tvStepDescription;

        @BindView(R.id.iv_step_icon)
        ImageView playIcon;

        public IngredientAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Log.e(TAG, "HERE onClick: \nRecipe: "+recipe+"\nindex: "+position+"\n Steps: "+steps);
            listener.onStepItemClick(steps,position,recipe);
        }
    }

}
