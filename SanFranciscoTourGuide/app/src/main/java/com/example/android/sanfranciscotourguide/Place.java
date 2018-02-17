package com.example.android.sanfranciscotourguide;

import java.io.Serializable;

/**
 * Created by sjani on 2/14/2018.
 */

public class Place implements Serializable {

    private String sightName;

    private int sightImageId;

    private String sightAddress;

    private String sightPhone;

    private String sightTime;

    private String sightWebsite;

    private boolean sightFavorite;

    private String sightInfo;

    public Place(String SightName, int SightImageId,String SightAddress,String SightPhone,String SightTime,String SightWesite,boolean isFavorite,String SightInfo){
        sightName = SightName;
        sightImageId = SightImageId;
        sightAddress = SightAddress;
        sightPhone = SightPhone;
        sightTime = SightTime;
        sightWebsite = SightWesite;
        sightFavorite = isFavorite;
        sightInfo = SightInfo;
    }

    public String getSightName(){
        return sightName;
    }

    public int getSightImageId() {
        return sightImageId;
    }

    public String getSightAddress(){ return sightAddress; }

    public String getSightPhone(){ return sightPhone; }

    public String getSightTime(){ return sightTime; }

    public String getSightWebsite(){ return sightWebsite; }

    public boolean getSightFavorite(){ return sightFavorite;}

    public void setSightFavorite(boolean isFavorite){
        sightFavorite = isFavorite;
    }
    public String getSightInfo(){ return sightInfo; }

}
