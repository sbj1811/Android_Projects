package com.example.android.popularmovies.Fragments;

import android.content.ContentValues;
import android.database.DatabaseUtils;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.Data.MovieContract;
import com.example.android.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by sjani on 5/5/2018.
 */

public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = DetailFragment.class.getSimpleName();

    boolean isMarkedFavorite;

    @BindView(R.id.tv_title)
    TextView titleTextView;

    @BindView(R.id.tv_overview) TextView overviewTextView;

    @BindView(R.id.tv_release_date) TextView releaseDateTextView;

    @BindView(R.id.tv_voting_average) TextView votingtextView;

    @BindView(R.id.iv_poster)
    ImageView posterImageView;

    @BindView(R.id.iv_backdrop) ImageView backdropImageView;

    @BindView(R.id.fav_button)
    FloatingActionButton favButton;

    private Uri uri;
    private int movieId;

    private Cursor movieCursor;
    private ContentValues values;

    private static final int DETAILS_LOADER_ID = 111;
    private static final int FAVORITE_LOADER_ID = 222;

    private static final String[] PROJECTION = new String[]{
            MovieContract.MovieEntry.COLUMN_MOVIE_ID,
            MovieContract.MovieEntry.COLUMN_TITLE,
            MovieContract.MovieEntry.COLUMN_RELEASE_DATE,
            MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE,
            MovieContract.MovieEntry.COLUMN_POSTER_PATH,
            MovieContract.MovieEntry.COLUMN_BACKDROP_PATH,
            MovieContract.MovieEntry.COLUMN_OVERVIEW
    };

    private static final int INDEX_MOVIE_ID = 0;
    private static final int INDEX_TITLE = 1;
    private static final int INDEX_RELEASE_DATE = 2;
    private static final int INDEX_VOTE_AVERAGE = 3;
    private static final int INDEX_POSTER_PATH = 4;
    private static final int INDEX_BACKDROP_PATH = 5;
    private static final int INDEX_OVERVIEW = 6;

    public DetailFragment() {
    }

    public static DetailFragment newInstance(Uri mUri, int id){
        Bundle arguments = new Bundle();
        arguments.putParcelable("URI",mUri);
        arguments.putInt("MOVIE_ID",id);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uri = getArguments().getParcelable("URI");
        movieId = getArguments().getInt("MOVIE_ID");
        getLoaderManager().initLoader(DETAILS_LOADER_ID,null,DetailFragment.this);
        getLoaderManager().initLoader(FAVORITE_LOADER_ID,null,DetailFragment.this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detail_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isMarkedFavorite){
                    favButton.setImageResource(R.drawable.ic_favorite_border);
                    isMarkedFavorite = false;
                    Uri uriToBeDeleted = MovieContract.MovieEntry.CONTENT_URI_FAVORITES.buildUpon().appendPath(String.valueOf(movieId)).build();
                    getActivity().getContentResolver().delete(uriToBeDeleted,null,null);
                }else {
                    favButton.setImageResource(R.drawable.ic_favorite);
                    isMarkedFavorite = true;
                    getActivity().getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI_FAVORITES,values);
                }
                isMarkedFavorite = !isMarkedFavorite;
            }
        });
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case DETAILS_LOADER_ID:
                return new CursorLoader(
                        getActivity(),
                        uri,
                        PROJECTION,
                        null, null, null);
            case FAVORITE_LOADER_ID:
                return new CursorLoader(
                        getActivity(),
                        MovieContract.MovieEntry.CONTENT_URI_FAVORITES,
                        PROJECTION,
                        MovieContract.MovieEntry.COLUMN_MOVIE_ID + "=?",
                        new String[]{String.valueOf(movieId)},
                        null);
            default:
                throw new RuntimeException("Loader not implemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null) {
            try {
                while (data.moveToNext()) {
                    if(String.valueOf(movieId).equals(data.getString(INDEX_MOVIE_ID))) {

                        int id = loader.getId();
                        switch (id) {
                            case DETAILS_LOADER_ID:
                                createDetailView(data);
                                movieCursor = data;
                                values = new ContentValues();
                                DatabaseUtils.cursorRowToContentValues(movieCursor,values);
                                Log.e(TAG, "onLoadFinished: "+values);
                                break;
                            case FAVORITE_LOADER_ID:
                                if (data.getCount() > 0) {
                                    favButton.setImageResource(R.drawable.ic_favorite);
                                    isMarkedFavorite = true;
                                } else {
                                    favButton.setImageResource(R.drawable.ic_favorite_border);
                                    isMarkedFavorite = false;
                                }
                                break;
                            default:
                                throw new RuntimeException("Loader not implemented: " + id);
                        }
                    }
                }
            } finally {
                data.close();
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void createDetailView(Cursor data){
        String title = data.getString(INDEX_TITLE);
        titleTextView.setText(title);
        String overview = data.getString(INDEX_OVERVIEW);
        if (overview.equals("false")){
            overviewTextView.setText(this.getString(R.string.no_overview));
        } else {
            overviewTextView.setText(overview);
        }
        String dateObject = data.getString(INDEX_RELEASE_DATE);
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
        String vote = " "+ data.getString(INDEX_VOTE_AVERAGE) + "/10";
        votingtextView.setText(vote);
        String posterImageUrl = this.getString(R.string.image_base_url) + data.getString(INDEX_POSTER_PATH);
        String backdropImageUrl = this.getString(R.string.image_base_url) + data.getString(INDEX_BACKDROP_PATH);
        Picasso.with(posterImageView.getContext())
                .load(posterImageUrl)
                .into(posterImageView);
        Picasso.with(backdropImageView.getContext())
                .load(backdropImageUrl)
                .into(backdropImageView);

    }

}
