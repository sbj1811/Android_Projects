
package com.example.android.popularmoviesstage1.Models;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MovieList implements Parcelable
{

    @SerializedName("page")
    @Expose
    public int page;
    @SerializedName("total_results")
    @Expose
    public int totalResults;
    @SerializedName("total_pages")
    @Expose
    public int totalPages;
    @SerializedName("results")
    @Expose
    public List<Movies> results = null;
    public final static Parcelable.Creator<MovieList> CREATOR = new Creator<MovieList>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MovieList createFromParcel(Parcel in) {
            return new MovieList(in);
        }

        public MovieList[] newArray(int size) {
            return (new MovieList[size]);
        }

    }
    ;

    protected MovieList(Parcel in) {
        this.page = ((int) in.readValue((int.class.getClassLoader())));
        this.totalResults = ((int) in.readValue((int.class.getClassLoader())));
        this.totalPages = ((int) in.readValue((int.class.getClassLoader())));
        in.readList(this.results, (Movies.class.getClassLoader()));
    }

    public MovieList() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<Movies> getResults() {
        return results;
    }

    public void setResults(List<Movies> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "MovieList{" +
                "page=" + page +
                ", totalResults=" + totalResults +
                ", totalPages=" + totalPages +
                ", results=" + results +
                '}';
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(page);
        dest.writeValue(totalResults);
        dest.writeValue(totalPages);
        dest.writeList(results);
    }

    public int describeContents() {
        return  0;
    }

}
