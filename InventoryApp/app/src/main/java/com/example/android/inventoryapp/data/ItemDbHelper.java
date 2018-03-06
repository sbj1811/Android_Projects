package com.example.android.inventoryapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sjani on 3/1/2018.
 */

public class ItemDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "item.db";

    public ItemDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_ENTRIES = "CREATE TABLE " + ItemContract.ItemEntry.TABLE_NAME+ " ("
                        + ItemContract.ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + ItemContract.ItemEntry.COLUMN_ITEM_NAME + " TEXT NOT NULL, "
                        + ItemContract.ItemEntry.COLUMN_ITEM_QUANTITY + " INTEGER NOT NULL DEFAULT 0, "
                        + ItemContract.ItemEntry.COLUMN_ITEM_PRICE + " FLOAT NOT NULL DEFAULT 0.00, "
                        + ItemContract.ItemEntry.COLUMN_ITEM_IMAGE + " TEXT);";

        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
