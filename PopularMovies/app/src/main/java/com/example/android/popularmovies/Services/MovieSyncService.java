package com.example.android.popularmovies.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.android.popularmovies.Adapters.MovieSyncAdapter;

/**
 * Created by sjani on 5/4/2018.
 */

public class MovieSyncService extends Service {

    private static final Object LOCK = new Object();
    private static MovieSyncAdapter syncAdapter;

    @Override
    public void onCreate() {
        synchronized (LOCK){
            syncAdapter = new MovieSyncAdapter(this,false);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return syncAdapter.getSyncAdapterBinder();
    }
}
