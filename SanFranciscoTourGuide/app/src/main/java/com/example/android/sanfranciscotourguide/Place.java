package com.example.android.sanfranciscotourguide;

/**
 * Created by sjani on 2/14/2018.
 */

public class Place {

    private String sightName;

    private int sightImageId;

    public Place(String SightName, int SightImageId){
        sightName = SightName;
        sightImageId = SightImageId;
    }

    public String getSightName(){
        return sightName;
    }

    public int getSightImageId() {
        return sightImageId;
    }
}
