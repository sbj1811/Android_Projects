package com.example.android.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by sjani on 2/21/2018.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private String mUrl;
    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        List<News> result = QueryUtils.fetchNewsData(mUrl);
        return result;
    }
}
