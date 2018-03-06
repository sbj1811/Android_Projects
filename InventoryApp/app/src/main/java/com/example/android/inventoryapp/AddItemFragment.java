package com.example.android.inventoryapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
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
 * Created by sjani on 3/1/2018.
 */

public class AddItemFragment extends DialogFragment {

    ImageView imageView;
    String imageString;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final View mainView = getActivity().getLayoutInflater().inflate(R.layout.add_layout,null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        Button imageSelectButton = (Button) mainView.findViewById(R.id.product_image);
        imageView = (ImageView) mainView.findViewById(R.id.product_image_view);

        imageSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        final Dialog addDialog = builder.setView(mainView)
                .setTitle(R.string.add_item)
                .setPositiveButton(R.string.add_item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        EditText editName = (EditText) mainView.findViewById(R.id.product_name);
                        EditText editQuantity = (EditText) mainView.findViewById(R.id.product_quantity);
                        EditText editPrice = (EditText) mainView.findViewById(R.id.product_price);

                        String name = editName.getText().toString().trim();
                        String quantityString = editQuantity.getText().toString().trim();
                        String priceString = editPrice.getText().toString().trim();

                        if (TextUtils.isEmpty(name)  || TextUtils.isEmpty(quantityString) || TextUtils.isEmpty(priceString)) {
                            Toast.makeText(getActivity(), getString(R.string.empty_field), Toast.LENGTH_SHORT).show();

                        } else {
                            Integer quantity = Integer.parseInt(quantityString);
                            Float price = Float.parseFloat(priceString);
                            ContentValues values = new ContentValues();
                            values.put(ItemContract.ItemEntry.COLUMN_ITEM_NAME, name);
                            values.put(ItemContract.ItemEntry.COLUMN_ITEM_QUANTITY, quantity);
                            values.put(ItemContract.ItemEntry.COLUMN_ITEM_PRICE, price);
                            values.put(ItemContract.ItemEntry.COLUMN_ITEM_IMAGE, imageString);
                            Log.e("ADDFRAGMENT", "ImageString: "+imageString);
                            getActivity().getContentResolver().insert(ItemContract.ItemEntry.CONTENT_URI, values);

                            dismiss();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AddItemFragment.this.getDialog().cancel();
                    }
                })
                .create();



        return addDialog;
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
                    imageView.setImageURI(selectedImage);
                    imageString = selectedImage.toString();
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Choose from Library",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
        imageView.setImageBitmap(thumbnail);
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
