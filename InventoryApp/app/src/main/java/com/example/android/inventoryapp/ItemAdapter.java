package com.example.android.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.example.android.inventoryapp.R;
import com.example.android.inventoryapp.data.ItemContract;

/**
 * Created by sjani on 3/1/2018.
 */

public class ItemAdapter extends CursorAdapter {

    public ItemAdapter(Context context, Cursor c){
        super(context,c,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        TextView itemName = (TextView) view.findViewById(R.id.item_name);
        final TextView itemQuantity = (TextView) view.findViewById(R.id.item_quantity);
        TextView itemPrice = (TextView) view.findViewById(R.id.item_price);
        ImageView itemImage =  (ImageView) view.findViewById(R.id.item_image);

        int idColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry._ID);
        int nameColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_NAME);
        int quantityColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_QUANTITY);
        int priceColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_PRICE);
        int imageColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_IMAGE);

        int currentId = cursor.getInt(idColumnIndex);
        final String currentName = cursor.getString(nameColumnIndex);
        final Integer currentQuantity = cursor.getInt(quantityColumnIndex);
        final Float currentPrice = cursor.getFloat(priceColumnIndex);
        final String currentImage = cursor.getString(imageColumnIndex);


        if(currentImage != null) {
            Log.e("ITEMADAPTER", "ImageString: "+currentImage);
            itemImage.setVisibility(View.VISIBLE);
            itemImage.setImageURI(Uri.parse(currentImage));
            //Picasso.with(context).load(Uri.parse(currentImage)).resize(80, 120).into(itemImage);
        }
        else {
            itemImage.setVisibility(View.VISIBLE);
            itemImage.setImageResource(R.drawable.dummy);
        }

        itemName.setText(currentName);
        itemQuantity.setText("Quantity: "+Integer.toString(currentQuantity));
        itemPrice.setText("Price: $"+Float.toString(currentPrice));
        Picasso.with(context).load(currentImage).into(itemImage);

        Button sellButton = (Button) view.findViewById(R.id.sale);
        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer id = Integer.parseInt(view.getTag().toString());
                ContentValues values = new ContentValues();
                Log.e("ADAPTER", "onClick: QUANTITY: "+currentQuantity );
                values.put(ItemContract.ItemEntry.COLUMN_ITEM_NAME, currentName);
                values.put(ItemContract.ItemEntry.COLUMN_ITEM_QUANTITY, (currentQuantity >= 1) ? currentQuantity-1:0);
                values.put(ItemContract.ItemEntry.COLUMN_ITEM_PRICE, currentPrice);
                values.put(ItemContract.ItemEntry.COLUMN_ITEM_IMAGE, currentImage);

                Uri currentUri = ContentUris.withAppendedId(ItemContract.ItemEntry.CONTENT_URI,id);

                int rowsAffected = context.getContentResolver().update(currentUri,values,null,null);
                Log.e("ADAPTER", "onClick: ROWS AFFECTED: "+rowsAffected );
                itemQuantity.setText("Quantity: "+Integer.toString(currentQuantity));
                if (rowsAffected == 0 || currentQuantity == 0) {
                    Toast.makeText(context, context.getString(R.string.sell_error), Toast.LENGTH_SHORT).show();
                }
            }
        });
        Object obj = cursor.getInt(cursor.getColumnIndex(ItemContract.ItemEntry._ID));
        sellButton.setTag(obj);
    }
}
