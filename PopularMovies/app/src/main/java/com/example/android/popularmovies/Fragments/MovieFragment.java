package com.example.android.popularmovies.Fragments;

import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.Activities.DetailActivity;
import com.example.android.popularmovies.Adapters.MovieSyncAdapter;
import com.example.android.popularmovies.Adapters.MovieViewAdapter;
import com.example.android.popularmovies.Data.MovieContract;
import com.example.android.popularmovies.Models.AccountModel;
import com.example.android.popularmovies.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by sjani on 4/28/2018.
 */

public class MovieFragment extends Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor>, MovieViewAdapter.GridItemClickListener {


    private static final String TAG = MovieFragment.class.getSimpleName();
    private static final String[] PROJECTION = new String[]{
            MovieContract.MovieEntry.COLUMN_MOVIE_ID,
            MovieContract.MovieEntry.COLUMN_POSTER_PATH
    };
    private static final Uri[] CONTENT_URI_VARIANTS = new Uri[]{
            MovieContract.MovieEntry.CONTENT_URI_POPULAR,
            MovieContract.MovieEntry.CONTENT_URI_TOP_RATED,
            MovieContract.MovieEntry.CONTENT_URI_FAVORITES
    };
    private static final int LOADER_ID = 1;
    @BindView(R.id.rv_main)
    RecyclerView recyclerView;
    @BindView(R.id.tv_error_message_display)
    TextView emptyTextView;
    @BindView(R.id.pb_loading_indicator)
    ProgressBar progressBar;
    private MovieViewAdapter movieViewAdapter;
    private Uri uri;
    private String sortOrder;
    private boolean mDualPane;

    public MovieFragment() {

    }

    public static MovieFragment newInstance(String mSortOrder,int position) {
        Bundle arguments = new Bundle();
        MovieFragment fragment = new MovieFragment();
        arguments.putParcelable("CONTENT_URI",CONTENT_URI_VARIANTS[position]);
        arguments.putString("SortOrder", mSortOrder);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AccountModel.createSyncAccount(getContext());
        MovieSyncAdapter.performSync();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uri = getArguments().getParcelable("CONTENT_URI");
        getLoaderManager().initLoader(LOADER_ID,null,this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_layout, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        movieViewAdapter = new MovieViewAdapter(this, getActivity());

        recyclerView.setAdapter(movieViewAdapter);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else if(getResources().getBoolean(R.bool.show_empty_fragment) && getResources().getBoolean(R.bool.large_screen)){
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }

        View detailsView = getActivity().findViewById(R.id.details);
        mDualPane = detailsView != null && detailsView.getVisibility() == View.VISIBLE;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
//        sortOrder = sharedPreferences.getString(
//                getString(R.string.settings_sort_key),
//                getString(R.string.settings_sort_popular)
//        );
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            sortOrder = bundle.getString("SortOrder");
        } else {
            Log.e(TAG, "BUNDLE: NULL");
        }
        if (sortOrder.equals("popular")) {
            uri = MovieContract.MovieEntry.CONTENT_URI_POPULAR;
            return new CursorLoader(getActivity(), uri, PROJECTION, null, null, null);
        } else if (sortOrder.equals("top_rated")) {
            uri = MovieContract.MovieEntry.CONTENT_URI_TOP_RATED;
            return new CursorLoader(getActivity(), uri, PROJECTION, null, null, null);
        } else {
            uri = MovieContract.MovieEntry.CONTENT_URI_FAVORITES;
            return new CursorLoader(getActivity(), uri, PROJECTION, null, null, null);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        movieViewAdapter.swapCursor(data);
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (movieViewAdapter != null) {
            movieViewAdapter.swapCursor(null);
        }
    }


    @Override
    public void onGridItemClick(int clickedMovieId) {
        if(mDualPane){
            DetailFragment detailFragment = DetailFragment.newInstance(uri,clickedMovieId);
            getFragmentManager().beginTransaction()
                    .add(R.id.details,detailFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null)
                    .commit();
        } else {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("URI", uri);
            intent.putExtra("MOVIE_ID", clickedMovieId);
            startActivity(intent);
        }
    }
}
