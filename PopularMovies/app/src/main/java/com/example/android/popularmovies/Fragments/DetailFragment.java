package com.example.android.popularmovies.Fragments;

import android.content.ContentValues;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.graphics.Typeface;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.Data.MovieContract;
import com.example.android.popularmovies.Models.Review;
import com.example.android.popularmovies.Models.ReviewList;
import com.example.android.popularmovies.Models.Trailer;
import com.example.android.popularmovies.Models.TrailerList;
import com.example.android.popularmovies.Networking.MovieDbApiConnection;
import com.example.android.popularmovies.Networking.NetworkUtils;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.Services.NotificationService;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by sjani on 5/5/2018.
 */

public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = DetailFragment.class.getSimpleName();

    boolean isMarkedFavorite;

    @BindView(R.id.tv_title)
    TextView titleTextView;

    @BindView(R.id.tv_overview)
    TextView overviewTextView;

    @BindView(R.id.tv_release_date)
    TextView releaseDateTextView;

    @BindView(R.id.tv_voting_average)
    TextView votingtextView;

    @BindView(R.id.iv_poster)
    ImageView posterImageView;

    @BindView(R.id.iv_backdrop)
    ImageView backdropImageView;

    @BindView(R.id.fav_button)
    FloatingActionButton favButton;

    @BindView(R.id.trailer_layout)
    LinearLayout trailerLayout;

    @BindView(R.id.review_layout)
    LinearLayout reviewLayout;

    private Uri uri;
    private int movieId;

    private Cursor movieCursor;
    private ContentValues values;

    private static final int DETAILS_LOADER_ID = 111;
    private static final int FAVORITE_LOADER_ID = 222;

    private static final String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";
    private static final String YOUTUBE_IMAGE_URL = "http://img.youtube.com/vi/%s/mqdefault.jpg";

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

    public static DetailFragment newInstance(Uri mUri, int id) {
        Bundle arguments = new Bundle();
        arguments.putParcelable("URI", mUri);
        arguments.putInt("MOVIE_ID", id);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uri = getArguments().getParcelable("URI");
        movieId = getArguments().getInt("MOVIE_ID");
        getLoaderManager().initLoader(DETAILS_LOADER_ID, null, DetailFragment.this);
        getLoaderManager().initLoader(FAVORITE_LOADER_ID, null, DetailFragment.this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
      return inflater.inflate(R.layout.detail_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isMarkedFavorite) {
                    favButton.setImageResource(R.drawable.ic_favorite_border);
                    isMarkedFavorite = false;
                    Uri uriToBeDeleted = MovieContract.MovieEntry.CONTENT_URI_FAVORITES.buildUpon().appendPath(String.valueOf(movieId)).build();
                    getActivity().getContentResolver().delete(uriToBeDeleted, null, null);
                } else {
                    favButton.setImageResource(R.drawable.ic_favorite);
                    isMarkedFavorite = true;
                    getActivity().getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI_FAVORITES, values);
                    NotificationService.notifyUserAboutUpdate(getContext());
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
                    if (String.valueOf(movieId).equals(data.getString(INDEX_MOVIE_ID))) {

                        int id = loader.getId();
                        switch (id) {
                            case DETAILS_LOADER_ID:
                                createDetailView(data);
                                getTrailersForMovie(movieId);
                                getReviewsForMovie(movieId);
                                movieCursor = data;
                                values = new ContentValues();
                                DatabaseUtils.cursorRowToContentValues(movieCursor, values);
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

    private void createDetailView(Cursor data) {
        String title = data.getString(INDEX_TITLE);
        titleTextView.setText(title);
        String overview = data.getString(INDEX_OVERVIEW);
        if (overview.equals("false")) {
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
        String vote = " " + data.getString(INDEX_VOTE_AVERAGE) + "/10";
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


    private void getTrailersForMovie(int movieId){
        String apiKey = getContext().getResources().getString(R.string.apiKey);
        MovieDbApiConnection.getApi().getTrailers(movieId, apiKey).enqueue(new Callback<TrailerList>() {
            @Override
            public void onResponse(Call<TrailerList> call, Response<TrailerList> response) {
                List<Trailer> trailerList = response.body().getTrailers();
                if (trailerList == null) {
                    TextView noTrailerView = new TextView(getContext());
                    noTrailerView.setText(getResources().getString(R.string.no_trailers));
                    trailerLayout.addView(noTrailerView);
                    return;
                }
                for (Trailer trailer : trailerList) {
                    LinearLayout layout = createTrailerLayout(trailer);
                    trailerLayout.addView(layout);
                }

            }

            @Override
            public void onFailure(Call<TrailerList> call, Throwable t) {
                TextView noTrailerView = new TextView(getContext());
                noTrailerView.setText(getResources().getString(R.string.no_trailers));
                noTrailerView.setTextColor(getResources().getColor(R.color.colorWhite));
                trailerLayout.addView(noTrailerView);
            }
        });


    }

    private LinearLayout createTrailerLayout(Trailer trailer) {
        String videoKey = trailer.getKey();
        String videoImageUrl = String.format(YOUTUBE_IMAGE_URL, videoKey);
        final String videoUrl = YOUTUBE_BASE_URL + videoKey;
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(8, 8, 8, 8);
        ImageView videoThumbnail = new ImageView(getContext());
        Picasso.with(videoThumbnail.getContext())
                .load(videoImageUrl)
                .into(videoThumbnail);
        layout.addView(videoThumbnail);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewVideo = new Intent(Intent.ACTION_VIEW,Uri.parse(videoUrl));
                startActivity(viewVideo);
            }
        });

        return layout;
    }


    private void getReviewsForMovie(int movieId){
        String apiKey = getContext().getResources().getString(R.string.apiKey);
        MovieDbApiConnection.getApi().getReviews(movieId, apiKey).enqueue(new Callback<ReviewList>() {
            @Override
            public void onResponse(Call<ReviewList> call, Response<ReviewList> response) {
                List<Review> reviewLists = response.body().getReviews();
                if (reviewLists == null) {
                    TextView noReviewView = new TextView(getContext());
                    noReviewView.setText(getResources().getString(R.string.no_reviews));
                    reviewLayout.addView(noReviewView);
                    return;
                }
;
                for (Review review : reviewLists) {
                    LinearLayout layout = createReviewLayout(review);
                    reviewLayout.addView(layout);
                }

            }

            @Override
            public void onFailure(Call<ReviewList> call, Throwable t) {
                TextView noReviewView = new TextView(getContext());
                noReviewView.setText(getResources().getString(R.string.no_reviews));
                noReviewView.setTextColor(getResources().getColor(R.color.colorWhite));
                reviewLayout.addView(noReviewView);
            }
        });


    }

    private LinearLayout createReviewLayout(Review review) {
        String author = review.getAuthor();
        String reviewContent = review.getContent();
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(8, 8, 8, 8);

        TextView authorView = new TextView(getContext());
        authorView.setText("By: "+author);
        authorView.setTextColor(getResources().getColor(R.color.colorText));
        authorView.setTypeface(null, Typeface.BOLD);
        authorView.setPadding(0,12,0,12);
        layout.addView(authorView);

        TextView contentView = new TextView(getContext());
        contentView.setText(reviewContent);
        contentView.setTextColor(getResources().getColor(R.color.colorText));
        contentView.setPadding(0,12,0,12);
        layout.addView(contentView);

        return layout;
    }

}
