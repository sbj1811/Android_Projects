package com.example.android.popularmovies.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.popularmovies.Data.MovieContract.MovieEntry;

/**
 * Created by sjani on 4/28/2018.
 */

public class MovieDbHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static String createTable(String tableName){
        return "CREATE TABLE " + tableName + " (" +
                        MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        MovieEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                        MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                        MovieEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                        MovieEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL, " +
                        MovieEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                        MovieEntry.COLUMN_BACKDROP_PATH + " TEXT NOT NULL, " +
                        MovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                        " UNIQUE (" + MovieEntry.COLUMN_MOVIE_ID + ") ON CONFLICT REPLACE);";
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(createTable(MovieEntry.TABLE_NAME_POPULAR));
        sqLiteDatabase.execSQL(createTable(MovieEntry.TABLE_NAME_TOP_RATED));
        sqLiteDatabase.execSQL(createTable(MovieEntry.TABLE_NAME_FAVORITES));

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME_POPULAR);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME_TOP_RATED);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME_FAVORITES);
        onCreate(sqLiteDatabase);
    }
}
