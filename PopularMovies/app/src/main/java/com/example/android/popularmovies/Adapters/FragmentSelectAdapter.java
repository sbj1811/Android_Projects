package com.example.android.popularmovies.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.popularmovies.Fragments.MovieFragment;
import com.example.android.popularmovies.R;

import java.text.SimpleDateFormat;

/**
 * Created by sjani on 4/28/2018.
 */

public class FragmentSelectAdapter extends FragmentPagerAdapter {

    private Context mContext;

    private String sortOrder;

    public FragmentSelectAdapter(FragmentManager fm, Context mContext) {
        super(fm);
        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            sortOrder = mContext.getString(R.string.settings_sort_popular);
            Fragment popularFragment =  MovieFragment.newInstance(sortOrder,position);
            return popularFragment;
        } else if (position == 1){
            sortOrder = mContext.getString(R.string.settings_sort_toprated);
            Fragment topratedFragment =  MovieFragment.newInstance(sortOrder,position);
            return topratedFragment;
        } else {
            sortOrder = mContext.getString(R.string.settings_sort_fav);
            Fragment favFragment =   MovieFragment.newInstance(sortOrder,position);
            return favFragment;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return mContext.getString(R.string.settings_sort_popular_header);
            case 1:
                return mContext.getString(R.string.settings_sort_toprated_header);
            case 2:
                return mContext.getString(R.string.settings_sort_fav_header);
            default:
                return null;
        }
    }
}
