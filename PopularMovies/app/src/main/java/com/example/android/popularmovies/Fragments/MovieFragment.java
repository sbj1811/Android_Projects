package com.example.android.popularmovies.Fragments;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.Activities.DetailActivity;
import com.example.android.popularmovies.Activities.MainActivity;
import com.example.android.popularmovies.Activities.SettingsActivity;
import com.example.android.popularmovies.Adapters.MovieViewAdapter;
import com.example.android.popularmovies.Models.Movies;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.Utils.MovieLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by sjani on 4/28/2018.
 */

public class MovieFragment extends Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<List<Movies>> {


    private static final String TAG = MovieFragment.class.getSimpleName();

    private MovieViewAdapter movieViewAdapter;
    private static final String MOVIE_KEY = "thisMovie";

    private static final int LOADER_ID = 1;

    private String sortOrder;

    @BindView(R.id.rv_main)
    RecyclerView recyclerView;

    @BindView(R.id.tv_error_message_display)
    TextView emptyTextView;

    @BindView(R.id.pb_loading_indicator)
    ProgressBar progressBar;

    public static MovieFragment newInstance(String mSortOrder) {
        Bundle arguments = new Bundle();
        MovieFragment fragment = new MovieFragment();
        arguments.putString("SortOrder",mSortOrder);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_layout, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sortOrder = getArguments().getString("SortOrder");
        Log.e(TAG, "SORTORDER: "+sortOrder );
        ButterKnife.bind(this,view);

//        recyclerView.setHasFixedSize(true);
//        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
//        recyclerView.setLayoutManager(layoutManager);

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }
        else{
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        }

        movieViewAdapter =  new MovieViewAdapter(new ArrayList<Movies>(), new MovieViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Movies movie) {
                //             Toast.makeText(MainActivity.this, "This is my Toast message!",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(),DetailActivity.class);
                intent.putExtra(MOVIE_KEY,movie);
                Log.e(TAG, "CLICK: "+movie);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(movieViewAdapter);

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        android.support.v4.app.LoaderManager loaderManager = getLoaderManager();
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            loaderManager.destroyLoader(LOADER_ID);
            loaderManager.initLoader(LOADER_ID, null,this);
        } else {
            recyclerView.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.VISIBLE);
            emptyTextView.setText(R.string.no_internet);
        }

    }

    @Override
    public Loader<List<Movies>> onCreateLoader(int id, Bundle args) {
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
//        sortOrder = sharedPreferences.getString(
//                getString(R.string.settings_sort_key),
//                getString(R.string.settings_sort_popular)
//        );
        String apiKey = this.getString(R.string.apiKey);
        return new MovieLoader(getContext(), sortOrder, apiKey);
    }

    @Override
    public void onLoadFinished(Loader<List<Movies>> loader, List<Movies> movies) {
        progressBar.setVisibility(View.GONE);
        emptyTextView.setText(R.string.no_movies);

        movieViewAdapter.clear();

        if (movies != null && !movies.isEmpty()) {
            recyclerView.setVisibility(View.VISIBLE);
            emptyTextView.setVisibility(View.GONE);
            movieViewAdapter.addAll(movies);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movies>> loader) {
        movieViewAdapter.clear();
    }
}
