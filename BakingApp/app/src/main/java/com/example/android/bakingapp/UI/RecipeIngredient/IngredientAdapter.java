package com.example.android.bakingapp.UI.RecipeIngredient;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.Models.Step;
import com.example.android.bakingapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sjani on 5/22/2018.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientAdapterViewHolder> {

    private static final String TAG = IngredientAdapter.class.getSimpleName();

    private Context context;
    private OnStepClickListener listener;
    private List<Step> steps;

    public IngredientAdapter(OnStepClickListener listener,Context context) {
        this.context = context;
        this.listener = listener;
    }

    public void getSteps(List<Step> mSteps){
        this.steps = mSteps;
        Log.e(TAG, "getSteps: STEPS: "+steps);
    }

    public interface OnStepClickListener {
        void OnStepClick(Step step);
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
            Log.e(TAG, "onBindViewHolder: DESCRIPTION: "+stepDescription);
            holder.tvStepDescription.setText(position+") "+stepDescription);
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

        public IngredientAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            listener.OnStepClick(steps.get(position));
        }
    }

}
