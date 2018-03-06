package com.example.android.inventoryapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.CancellationSignal;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by sjani on 3/1/2018.
 */

public class ItemProvider extends ContentProvider {

    public static final String LOG_TAG = ItemProvider.class.getSimpleName();

    private static final int ITEMS = 100;
    private static final int ITEM_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private ItemDbHelper mDbHelper;

    static {
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_ITEMS, ITEMS);
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_ITEMS+"/#", ITEM_ID);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new ItemDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS:
                cursor = database.query(ItemContract.ItemEntry.TABLE_NAME, projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case ITEM_ID:
                selection = ItemContract.ItemEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                cursor = database.query(ItemContract.ItemEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType( Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS:
                return ItemContract.ItemEntry.CONTENT_LIST_TYPE;
            case ITEM_ID:
                return ItemContract.ItemEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert( Uri uri,  ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS:
                return insertItem(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertItem(Uri uri,  ContentValues contentValues){
        String name = contentValues.getAsString(ItemContract.ItemEntry.COLUMN_ITEM_NAME);
        if (name == null) {
            throw new IllegalArgumentException("Item requires a name");
        }
        Integer quantity = contentValues.getAsInteger(ItemContract.ItemEntry.COLUMN_ITEM_QUANTITY);
        if (quantity == null || quantity < 0) {
            throw new IllegalArgumentException("Item requires a name");
        }
        Float price = contentValues.getAsFloat(ItemContract.ItemEntry.COLUMN_ITEM_PRICE);
        if (price == null|| price < 0) {
            throw new IllegalArgumentException("Item requires a name");
        }

        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        long id = database.insert(ItemContract.ItemEntry.TABLE_NAME,null,contentValues);
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete( Uri uri,  String selection,  String[] selectionArgs) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS:
                rowsDeleted =  database.delete(ItemContract.ItemEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case ITEM_ID:
                selection = ItemContract.ItemEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted =  database.delete(ItemContract.ItemEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Delete is not supported for " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;

    }

    @Override
    public int update( Uri uri,  ContentValues contentValues,  String selection,  String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS:
                return updatePet(uri, contentValues, selection, selectionArgs);
            case ITEM_ID:
                selection = ItemContract.ItemEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updatePet(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updatePet(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs){
        if (contentValues.size() == 0) {
            return 0;
        }

        if (contentValues.containsKey(ItemContract.ItemEntry.COLUMN_ITEM_NAME)) {
            String name = contentValues.getAsString(ItemContract.ItemEntry.COLUMN_ITEM_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Item requires a name");
            }
        }
        if (contentValues.containsKey(ItemContract.ItemEntry.COLUMN_ITEM_QUANTITY)) {
            Integer quantity = contentValues.getAsInteger(ItemContract.ItemEntry.COLUMN_ITEM_QUANTITY);
            if (quantity == null || quantity < 0) {
                throw new IllegalArgumentException("Item requires a name");
            }
        }
        if (contentValues.containsKey(ItemContract.ItemEntry.COLUMN_ITEM_PRICE)) {
            Float price = contentValues.getAsFloat(ItemContract.ItemEntry.COLUMN_ITEM_PRICE);
            if (price == null || price < 0) {
                throw new IllegalArgumentException("Item requires a name");
            }
        }

        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        int rowsUpdated = database.update(ItemContract.ItemEntry.TABLE_NAME, contentValues, selection, selectionArgs);

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }
}
