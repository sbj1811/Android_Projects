package com.example.android.quakereport;

/**
 * Created by sjani on 2/18/2018.
 */

public class Earthquake {
    private double quakeMmagnitude;
    private String quakeLocation;
    private long quakeDate;
    private String quakeUrl;

    private static final String LOG_TAG = Earthquake.class.getName();

    public Earthquake(double quakemmagnitude, String quakelocation, long quakedate, String quakeurl){
        quakeMmagnitude = quakemmagnitude;
        quakeLocation = quakelocation;
        quakeDate = quakedate;
        quakeUrl = quakeurl;
    }

    public double getQuakeMmagnitude(){
        return quakeMmagnitude;
    }

    public String getQuakeLocation() {
        return quakeLocation;
    }

    public long getQuakeDate() {
        return quakeDate;
    }

    public String getQuakeUrl() {
        return quakeUrl;
    }
}
