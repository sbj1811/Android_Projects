package com.example.android.inventoryapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.inventoryapp.data.ItemContract;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by sjani on 3/2/2018.
 */

public class EditActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Uri currentUri;
    private EditText editName;
    private EditText editQuantity;
    private EditText editPrice;
    private ImageView editImage;
    private Button save;
    private Button orderMore;
    private Button delete;

    String imageString;
    private static final int ITEM_LOADER = 0;

    private boolean ItemHasChanged = false;

    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            ItemHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e("EDITACTIVITY", "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();
        currentUri = intent.getData();
        Log.e("EDITACTIVITY", "onItemClick: ID= "+currentUri );
        setTitle("Edit Item");
        getLoaderManager().initLoader(ITEM_LOADER, null, this);

        editName = (EditText) findViewById(R.id.edit_item_name);
        editQuantity = (EditText) findViewById(R.id.edit_item_quantity);
        editPrice = (EditText) findViewById(R.id.edit_item_price);
        editImage = (ImageView) findViewById(R.id.edit_item_image);
        save = (Button) findViewById(R.id.save);
        orderMore = (Button) findViewById(R.id.order_more);
        delete = (Button) findViewById(R.id.delete);

        editName.setOnTouchListener(touchListener);
        editQuantity.setOnTouchListener(touchListener);
        editPrice.setOnTouchListener(touchListener);
        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveItem();
                finish();
            }
        });

        orderMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderMoreItems();
                finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deteleItem();
            }
        });

    }

    public void saveItem(){
        Log.e("EDITACTIVITY", "saveItem: ");
        String itemName = editName.getText().toString().trim();
        String itemQuantity = editQuantity.getText().toString().trim();
        String itemprice = editPrice.getText().toString().trim();

        if (TextUtils.isEmpty(itemName)  || TextUtils.isEmpty(itemQuantity) || TextUtils.isEmpty(itemprice)) {
            Toast.makeText(this, getString(R.string.empty_field), Toast.LENGTH_SHORT).show();
            return;
        }

        Integer quantity = Integer.parseInt(itemQuantity);
        Float price = Float.parseFloat(itemprice);
        ContentValues values = new ContentValues();



        values.put(ItemContract.ItemEntry.COLUMN_ITEM_NAME, itemName);
        values.put(ItemContract.ItemEntry.COLUMN_ITEM_QUANTITY, quantity);
        values.put(ItemContract.ItemEntry.COLUMN_ITEM_PRICE, price);
        values.put(ItemContract.ItemEntry.COLUMN_ITEM_IMAGE, imageString);

        int rowsAffected = getContentResolver().update(currentUri, values, null, null);

        // Show a toast message depending on whether or not the update was successful.
        if (rowsAffected == 0) {
            // If no rows were affected, then there was an error with the update.
            Toast.makeText(this, "Error Updating item",
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the update was successful and we can display a toast.
            Toast.makeText(this, "Item Updated",
                    Toast.LENGTH_SHORT).show();
        }


    }

    public void orderMoreItems(){
        String itemName = editName.getText().toString().trim();
        String itemQuantity = editQuantity.getText().toString().trim();
        String subject = "Inventory Order for "+itemName;
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body(itemName,itemQuantity));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public String body(String name, String quantity){
        String message = "";
        message += "Hello,\n";
        message += "I would like to order following item:\n";
        message += "Name: "+name+"\n";
        message += "Quantity: "+quantity+"\n";
        message += "Thank You!";
        return message;
    }

    public void deteleItem(){
        if (currentUri != null) {

            int rowsDeleted = getContentResolver().delete(currentUri, null, null);

            if (rowsDeleted == 0) {
                Toast.makeText(this, "Error Deleting item",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Item Deleted",
                        Toast.LENGTH_SHORT).show();
            }
        }

        // Close the activity
        finish();

    }




    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Log.e("EDITACTIVITY", "onCreateLoader: ");
        String[] projection = {
                ItemContract.ItemEntry._ID,
                ItemContract.ItemEntry.COLUMN_ITEM_NAME,
                ItemContract.ItemEntry.COLUMN_ITEM_QUANTITY,
                ItemContract.ItemEntry.COLUMN_ITEM_PRICE,
                ItemContract.ItemEntry.COLUMN_ITEM_IMAGE
        };
        Log.e("EDITACTIVITY", "onCreateLoader: "+projection[0]+" "+projection[1]+" "+projection[2]+" "+projection[3]);
        return new CursorLoader(this, ItemContract.ItemEntry.CONTENT_URI,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.e("EDITACTIVITY", "onLoadFinished: ");
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        if (cursor.moveToFirst()) {

            int nameColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_NAME);
            int quantityColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_QUANTITY);
            int priceColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_PRICE);
            int imageColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_IMAGE);
            Log.e("EDITACTIVITY", "onLoadFinished: "+nameColumnIndex+" "+quantityColumnIndex+" "+priceColumnIndex);

            String editCurrentName = cursor.getString(nameColumnIndex);
            int editCurrentQuantity = cursor.getInt(quantityColumnIndex);
            Float editCurrentPrice = cursor.getFloat(priceColumnIndex);
            String editCurrentImage = cursor.getString(imageColumnIndex);
            Log.e("EDITACTIVITY", "onLoadFinished: "+editCurrentName+" "+editCurrentQuantity+" "+editCurrentPrice);

            if(editCurrentImage != null) {
                editImage.setVisibility(View.VISIBLE);
                editImage.setImageURI(Uri.parse(editCurrentImage));
                //Picasso.with(context).load(Uri.parse(currentImage)).resize(80, 120).into(itemImage);
            }
            else {
                editImage.setVisibility(View.VISIBLE);
                editImage.setImageResource(R.drawable.dummy);
            }
            editName.setText(editCurrentName);
            editQuantity.setText(Integer.toString(editCurrentQuantity));
            editPrice.setText(Float.toString(editCurrentPrice));

        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.e("EDITACTIVITY", "onLoaderReset: ");
        editName.setText("");
        editQuantity.setText("");
        editPrice.setText("");
        editImage.setImageDrawable(null);

    }




    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode == Activity.RESULT_OK){
                    onCaptureImageResult(imageReturnedIntent);
                }

                break;
            case 1:
                if(resultCode == Activity.RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    editImage.setImageURI(selectedImage);
                    imageString = selectedImage.toString();
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Choose from Library",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
//                if (items[item].equals("Take Photo")) {
//                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(takePicture, 0);
//                } else
                if (items[item].equals("Choose from Library")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        editImage.setImageBitmap(thumbnail);
        imageString = BitMapToString(thumbnail);
    }


    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
}
