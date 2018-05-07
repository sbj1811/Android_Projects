package com.example.android.popularmovies.Activities;



import android.app.Fragment;
import android.content.res.Configuration;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.util.Log;


import com.example.android.popularmovies.Adapters.FragmentSelectAdapter;
import com.example.android.popularmovies.Fragments.EmptyFragment;
import com.example.android.popularmovies.R;
import com.facebook.stetho.Stetho;


import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

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
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build()
        );

        Fragment fragment = getFragmentManager().findFragmentById(R.id.details);

        boolean showEmptyFragment = getResources().getBoolean(R.bool.show_empty_fragment);
        if (showEmptyFragment && fragment == null) {
            EmptyFragment emptyFragment = new EmptyFragment();
            this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details, emptyFragment).commit();
        }


    }
}