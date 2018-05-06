package com.example.android.popularmovies.Services;

import android.accounts.Account;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.android.popularmovies.Networking.MovieAccountAuthenticator;

/**
 * Created by sjani on 5/4/2018.
 */

public class MovieAuthenticatorService extends Service {



    private MovieAccountAuthenticator mAuthenticator;



    @Override
    public void onCreate() {
        super.onCreate();
        mAuthenticator = new MovieAccountAuthenticator(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
