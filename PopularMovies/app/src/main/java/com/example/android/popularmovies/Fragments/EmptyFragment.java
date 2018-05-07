package com.example.android.popularmovies.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.popularmovies.R;

/**
 * Created by sjani on 5/6/2018.
 */

public class EmptyFragment extends Fragment {

    private static final String TAG = EmptyFragment.class.getSimpleName();

    public EmptyFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.empty_fragment,container,false);
    }
}
