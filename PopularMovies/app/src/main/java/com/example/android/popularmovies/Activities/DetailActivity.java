package com.example.android.popularmovies.Activities;

import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Movie;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.Data.MovieContract;
import com.example.android.popularmovies.Fragments.DetailFragment;
import com.example.android.popularmovies.Models.Movies;
import com.example.android.popularmovies.R;
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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_main);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Uri uri = extras.getParcelable("URI");
        int movieId = intent.getIntExtra("MOVIE_ID",0);

        DetailFragment detailFragment = DetailFragment.newInstance(uri,movieId);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.detail_container,detailFragment)
                .commit();


    }
}
