package com.example.android.inventoryapp;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.inventoryapp.data.ItemContract;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int ITEM_LOADER = 0;

    ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddItemFragment itemFragment = new AddItemFragment();
                itemFragment.show(getFragmentManager(),getString(R.string.add_item));
            }
        });

        ListView itemListView = (ListView) findViewById(R.id.list);

        itemAdapter = new ItemAdapter(this, null);
        itemListView.setAdapter(itemAdapter);

        View emptyView = findViewById(R.id.empty_view);
        itemListView.setEmptyView(emptyView);

        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,EditActivity.class);
                Log.e("MAINACTIVITY", "onItemClick: ID= "+id);
                Uri uri = ContentUris.withAppendedId(ItemContract.ItemEntry.CONTENT_URI,id);
                intent.setData(uri);
                intent.putExtra("selected_item_id",id);
                startActivity(intent);

            }
        });

        getLoaderManager().initLoader(ITEM_LOADER,null,this);

    }

    private void insert_dummy(){
        ContentValues values = new ContentValues();
        values.put(ItemContract.ItemEntry.COLUMN_ITEM_NAME, "Book");
        values.put(ItemContract.ItemEntry.COLUMN_ITEM_QUANTITY, "5");
        values.put(ItemContract.ItemEntry.COLUMN_ITEM_PRICE, "4.5");
        values.put(ItemContract.ItemEntry.COLUMN_ITEM_IMAGE, R.drawable.dummy);

        Uri newUri = getContentResolver().insert(ItemContract.ItemEntry.CONTENT_URI, values);
    }

    private void deleteAllItems(){
        int rowsDeleted = getContentResolver().delete(ItemContract.ItemEntry.CONTENT_URI, null, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_insert_dummy_data:
                insert_dummy();
                return true;
            case R.id.action_delete_all_entries:
                deleteAllItems();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                ItemContract.ItemEntry._ID,
                ItemContract.ItemEntry.COLUMN_ITEM_NAME,
                ItemContract.ItemEntry.COLUMN_ITEM_QUANTITY,
                ItemContract.ItemEntry.COLUMN_ITEM_PRICE,
                ItemContract.ItemEntry.COLUMN_ITEM_IMAGE
        };

        return new CursorLoader(this, ItemContract.ItemEntry.CONTENT_URI,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        itemAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        itemAdapter.swapCursor(null);
    }
}
