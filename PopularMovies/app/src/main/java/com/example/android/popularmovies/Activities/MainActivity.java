package com.example.android.popularmovies.Activities;



import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;


import com.example.android.popularmovies.Adapters.FragmentSelectAdapter;
import com.example.android.popularmovies.R;


import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.main_tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.main_viewpager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        FragmentSelectAdapter selectAdapter = new FragmentSelectAdapter(getSupportFragmentManager(),this);
        mViewPager.setAdapter(selectAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

    }





}
