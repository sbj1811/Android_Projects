package com.example.android.newsapp;

/**
 * Created by sjani on 2/20/2018.
 */

public class News {

    private String newsHeadline;

    private String newsSection;

    private String newsType;

    private String newsDate;

    private String newsUrl;


    public News(String newsheadline, String newssection, String newstype, String newsdate, String newsurl) {
        this.newsHeadline = newsheadline;
        this.newsSection = newssection;
        this.newsType = newstype;
        this.newsDate = newsdate;
        this.newsUrl= newsurl;
    }

    public String getNewsHeadline() {
        return newsHeadline;
    }

    public String getNewsSection() {
        return newsSection;
    }

    public String getNewsType() {
        return newsType;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public String getNewsUrl() {
        return newsUrl;
    }
}
