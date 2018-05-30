package com.example.android.bakingapp.UI.RecipeList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.android.bakingapp.Models.Recipe;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.Utils.ItemClickListener;
import com.example.android.bakingapp.Utils.Utility;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sjani on 5/21/2018.
 */

public class ListRecipeAdapter extends RecyclerView.Adapter<ListRecipeAdapter.ListAdapterViewHolder> {

    private static final String TAG = ListRecipeAdapter.class.getSimpleName();

    private ArrayList<Recipe> recipeList;
    final ItemClickListener listener;
    private Context context;

    public ListRecipeAdapter(ItemClickListener mListener, Context mContext) {
        this.listener = mListener;
        this.context = mContext;
    }

    public void setRecipes (ArrayList<Recipe> recipe){
        this.recipeList = recipe;
        notifyDataSetChanged();
    }


    @Override
    public ListAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        ListAdapterViewHolder holder = new ListAdapterViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ListAdapterViewHolder holder, int position) {
        String title = recipeList.get(position).getName();
        holder.recipeTitle.setText(title);
        int serving = recipeList.get(position).getServings();
        holder.recipeServing.setText(String.format(Locale.US, holder.servingsText, serving));
        if(Utility.checkRecipeExist(context,recipeList.get(position).getId())){
            holder.favFrameLayout.setVisibility(View.VISIBLE);
        } else {
            holder.favFrameLayout.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if (recipeList == null){
            return 0;
        }
        return recipeList.size();
    }

    public class ListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.recipe_title)
        TextView recipeTitle;

        @BindView(R.id.recipe_serving)
        TextView recipeServing;

        @BindView(R.id.list_item_framelayout)
        FrameLayout favFrameLayout;

        @BindString(R.string.servings_text)
        String servingsText;



        public ListAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            listener.onItemClick(recipeList.get(position),position);
        }
    }

}