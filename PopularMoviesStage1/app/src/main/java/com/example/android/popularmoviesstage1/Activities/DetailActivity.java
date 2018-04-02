package com.example.android.popularmoviesstage1.Activities;

import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmoviesstage1.Models.Movies;
import com.example.android.popularmoviesstage1.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sjani on 3/29/2018.
 */

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();

    private static final String MOVIE_KEY = "thisMovie";

    @BindView(R.id.tv_title) TextView titleTexyView;

    @BindView(R.id.tv_overview) TextView overviewTextView;

    @BindView(R.id.tv_release_date) TextView releaseDateTextView;

    @BindView(R.id.tv_voting_average) TextView votingtextView;

    @BindView(R.id.iv_poster) ImageView posterImageView;

    @BindView(R.id.iv_backdrop) ImageView backdropImageView;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_main);

        ButterKnife.bind(this);
        Movies movie =  (Movies) getIntent().getParcelableExtra("thisMovie");
        if (movie != null) {
            titleTexyView.setText(movie.getTitle());
            overviewTextView.setText(movie.getOverview());
            releaseDateTextView.setText(movie.getReleaseDate());
            String vote = movie.getVoteAverage() + "/7";
            votingtextView.setText(vote);
            String posterImageUrl = R.string.image_base_url + movie.getPosterPath();
            String backdropImageUrl = R.string.image_base_url + movie.getBackdropPath();
            Picasso.with(posterImageView.getContext())
                    .load(posterImageUrl)
                    .into(posterImageView);
            Picasso.with(backdropImageView.getContext())
                    .load(backdropImageUrl)
                    .into(backdropImageView);
        } else {
            Toast.makeText(DetailActivity.this, "This is my Toast message!",Toast.LENGTH_LONG).show();
            Log.e(TAG, "onCreate: "+movie);
        }

    }
}
