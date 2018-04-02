package com.example.android.popularmoviesstage1.Adapters;

import android.content.Context;
import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.example.android.popularmoviesstage1.Models.Movies;
import com.example.android.popularmoviesstage1.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sjani on 3/30/2018.
 */

public class MovieViewAdapter extends RecyclerView.Adapter<MovieViewAdapter.MovieViewAdapterViewHolder> {

    private static final String TAG = MovieViewAdapter.class.getSimpleName();

    private List<Movies> moviesList;

    private  OnItemClickListener mclickHandler;

    private Context context;

    public interface OnItemClickListener {
        void onItemClick(Movies movie);
    }

    public MovieViewAdapter (List<Movies> movies, OnItemClickListener listener){
        moviesList = movies;
        mclickHandler = listener;
    }

    @Override
    public MovieViewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context =  parent.getContext();
        View movieView = LayoutInflater.from(context).inflate(R.layout.grid_item,parent,false);
        MovieViewAdapterViewHolder viewHolder = new MovieViewAdapterViewHolder(movieView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewAdapterViewHolder holder, int position) {
        Movies movie = moviesList.get(position);
        String posterImageUrl = context.getString(R.string.image_base_url)+movie.getPosterPath();
        Picasso.with(holder.imageView.getContext())
                .load(posterImageUrl)
                .into(holder.imageView);
        holder.bind(movie,mclickHandler);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }



    public class MovieViewAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_grid)
        ImageView imageView;

        public MovieViewAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }


        public void bind (final Movies movie, final OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(movie);
                }
            });
        }


    }

    public void clear(){
        moviesList.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Movies> movieslist){
        moviesList = movieslist;
        notifyDataSetChanged();
    }
}
