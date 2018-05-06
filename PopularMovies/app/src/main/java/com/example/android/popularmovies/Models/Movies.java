
package com.example.android.popularmovies.Models;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Movies implements Parcelable
{

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("vote_average")
    @Expose
    private float voteAverage;
    @SerializedName("title")
    @Expose
    private String title;
     @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("original_title")
    @Expose
    private String originalTitle;
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    public final static Parcelable.Creator<Movies> CREATOR = new Creator<Movies>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        public Movies[] newArray(int size) {
            return (new Movies[size]);
        }

    }
    ;

    protected Movies(Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.voteAverage = ((float) in.readValue((float.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.posterPath = ((String) in.readValue((String.class.getClassLoader())));
        this.originalTitle = ((String) in.readValue((String.class.getClassLoader())));
        this.backdropPath = ((String) in.readValue((String.class.getClassLoader())));
        this.overview = ((String) in.readValue((String.class.getClassLoader())));
        this.releaseDate = ((String) in.readValue((String.class.getClassLoader())));
    }

    public int getId() {
        return id;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getOverview() {
        return overview;
    }

     public String getReleaseDate() {
        return releaseDate;
    }

    @Override
    public String toString() {
        return "Movies{" + "\n" +
                ", id=" + id + "\n" +
                ", voteAverage=" + voteAverage + "\n" +
                ", title='" + title + '\'' + "\n" +
                ", posterPath='" + posterPath + '\'' + "\n" +
                ", originalTitle='" + originalTitle + '\'' + "\n" +
                ", backdropPath='" + backdropPath + '\'' + "\n" +
                ", overview='" + overview + '\'' + "\n" +
                ", releaseDate='" + releaseDate + '\'' + "\n" +
                '}';
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(voteAverage);
        dest.writeValue(title);
        dest.writeValue(posterPath);
        dest.writeValue(originalTitle);
        dest.writeValue(backdropPath);
        dest.writeValue(overview);
        dest.writeValue(releaseDate);
    }

    public int describeContents() {
        return  0;
    }

}
