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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        Intent intent = getIntent();
        Movies movie =  (Movies) intent.getParcelableExtra("thisMovie");
        if (movie != null) {
            Log.e(TAG, "Movies: "+movie.toString());
            titleTexyView.setText(movie.getTitle());
            if (movie.getOverview().equals("false")){
                overviewTextView.setText(this.getString(R.string.no_overview));
            } else {
                overviewTextView.setText(movie.getOverview());
            }
            String dateObject = movie.getReleaseDate();
            String date = "";
            try {
                final SimpleDateFormat sdf_date_1 = new SimpleDateFormat("yyyy-MM-dd");
                final Date dateObj = sdf_date_1.parse(dateObject);
                final SimpleDateFormat sdf_date_2 = new SimpleDateFormat("MMM dd, yyyy");
                date = " " + sdf_date_2.format(dateObj);
            } catch (ParseException p) {
                Log.e(TAG, "Problem parsing date", p);
            }
            releaseDateTextView.setText(date);
            String vote = " "+ movie.getVoteAverage() + "/10";
            votingtextView.setText(vote);
            String posterImageUrl = this.getString(R.string.image_base_url) + movie.getPosterPath();
            String backdropImageUrl = this.getString(R.string.image_base_url) + movie.getBackdropPath();
            Log.e(TAG, "posterImageUrl: "+posterImageUrl);
            Log.e(TAG, "backdropImageUrl: "+backdropImageUrl);
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
