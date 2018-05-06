package com.example.android.popularmovies.Adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Movie;
import android.support.transition.Transition;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.example.android.popularmovies.Data.MovieContract;
import com.example.android.popularmovies.Models.Movies;
import com.example.android.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sjani on 3/30/2018.
 */

public class MovieViewAdapter extends RecyclerView.Adapter<MovieViewAdapter.MovieViewAdapterViewHolder> {

    private static final String TAG = MovieViewAdapter.class.getSimpleName();

  //  private List<Movies> moviesList;

    private  final GridItemClickListener mclickHandler;

    private Context context;

    private Cursor cursor;

    public interface GridItemClickListener {
        void onGridItemClick(int clickedMovieId);
    }

    public MovieViewAdapter (GridItemClickListener listener, Context mContext){
        context = mContext;
        mclickHandler = listener;
    }

    @Override
    public MovieViewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(context).inflate(R.layout.grid_item,parent,false);
        MovieViewAdapterViewHolder viewHolder = new MovieViewAdapterViewHolder(movieView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewAdapterViewHolder holder, int position) {
    //    Movies movie = moviesList.get(position);
        int movieIdIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID);
        int posterIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH);
        cursor.moveToPosition(position);
        String posterImageUrl = context.getString(R.string.image_base_url)+cursor.getString(posterIndex);
        Picasso.with(holder.imageView.getContext())
                .load(posterImageUrl)
                .into(holder.imageView);
  //      holder.bind(movie,mclickHandler);
    }

    @Override
    public int getItemCount() {
        if(cursor == null){
            return 0;
        }
        return cursor.getCount();
    }

    public Cursor swapCursor(Cursor c) {
        if (cursor == c) {
            return null;
        }

        Cursor temp = cursor;
        this.cursor = c;

        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }

    public class MovieViewAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_grid)
        ImageView imageView;

        public MovieViewAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            cursor.moveToPosition(adapterPosition);
            int index = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID);
            int movieId = cursor.getInt(index);
            mclickHandler.onGridItemClick(movieId);
        }
    }


}
