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

    private String newsThumbnail;


    public News(String newsheadline, String newssection, String newstype, String newsdate, String newsurl, String newsthumbnail) {
        this.newsHeadline = newsheadline;
        this.newsSection = newssection;
        this.newsType = newstype;
        this.newsDate = newsdate;
        this.newsUrl = newsurl;
        this.newsThumbnail = newsthumbnail;
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

    public String getNewsThumbnail() {
        return newsThumbnail;
    }
}
