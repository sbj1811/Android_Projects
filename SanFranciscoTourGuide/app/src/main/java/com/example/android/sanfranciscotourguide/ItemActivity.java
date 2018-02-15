package com.example.android.sanfranciscotourguide;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.Serializable;

/**
 * Created by sjani on 2/14/2018.
 */

public class ItemActivity extends AppCompatActivity implements Serializable {

    private Place places;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_activity);

        Intent i = getIntent();
        places = (Place) i.getSerializableExtra("sampleObject");

        TextView nameView = (TextView) findViewById(R.id.name_text_view);
        nameView.setText(places.getSightName());

        TextView websiteView = (TextView) findViewById(R.id.website_text_view);
        //   websiteView.setText(places.getSightWebsite());

        TextView addressView = (TextView) findViewById(R.id.address_text_view);
        addressView.setText(places.getSightAddress());

        TextView timeView = (TextView) findViewById(R.id.time_text_view);
        timeView.setText(places.getSightTime());

        TextView call2View = (TextView) findViewById(R.id.call_text_view_2);
        call2View.setText(places.getSightPhone());

        ImageView imageView = (ImageView) findViewById(R.id.image);
        imageView.setImageResource(places.getSightImageId());

        final LinearLayout favLinearLayout = (LinearLayout) findViewById(R.id.fav_linear_view);
        final ToggleButton toggleButton = (ToggleButton) findViewById(R.id.fav_togglebox);
        boolean isAlreadyChecked = places.getSightFavorite();
        if(isAlreadyChecked){
            toggleButton.setChecked(true);
            toggleButton.setBackgroundResource(R.drawable.ic_favorite_white_24dp);
        } else {
            toggleButton.setChecked(false);
            toggleButton.setBackgroundResource(R.drawable.ic_favorite_border_white_24dp);
        }

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            boolean isAlreadyChecked = places.getSightFavorite();
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    if (isAlreadyChecked){
                        places.setSightFavorite(false);
                        toggleButton.setBackgroundResource(R.drawable.ic_favorite_border_white_24dp);
                        return;
                    }
                    places.setSightFavorite(true);
                    toggleButton.setBackgroundResource(R.drawable.ic_favorite_white_24dp);
                } else {
                    places.setSightFavorite(false);
                    toggleButton.setBackgroundResource(R.drawable.ic_favorite_border_white_24dp);
                }
            }
        });


        final LinearLayout websiteLinearLayout = (LinearLayout) findViewById(R.id.website_linear_view);
        websiteLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(places.getSightWebsite()));
                startActivity(intent);
            }
        });

        final LinearLayout callLinearLayout = (LinearLayout) findViewById(R.id.call_linear_view);
        callLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+ places.getSightPhone()));
                startActivity(intent);
            }
        });

        final LinearLayout  addressLineraLayout = (LinearLayout) findViewById(R.id.address_linear_view);
        addressLineraLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q="+ places.getSightAddress());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        final LinearLayout callLinearLayout2 = (LinearLayout) findViewById(R.id.call_linear_view_2);
        callLinearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+ places.getSightPhone()));
                startActivity(intent);
            }
        });




    }
}
