package com.example.android.sanfranciscotourguide;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by sjani on 2/14/2018.
 */

public class SectionAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public SectionAdapter (Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new AttractionFragment();
        } else if (position == 1){
            return new ActivitiesFragment();
        } else if (position == 2){
            return new FoodFragment();
        } else {
            return new NightlifeFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return mContext.getString(R.string.category_attraction);
            case 1:
                return mContext.getString(R.string.category_activities);
            case 2:
                return mContext.getString(R.string.category_food);
            case 3:
                return mContext.getString(R.string.category_nightlife);
            default:
                return null;
        }
    }

}
