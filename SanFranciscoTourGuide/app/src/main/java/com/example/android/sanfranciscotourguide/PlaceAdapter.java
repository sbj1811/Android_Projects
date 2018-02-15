package com.example.android.sanfranciscotourguide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

/**
 * Created by sjani on 2/14/2018.
 */

public class PlaceAdapter extends ArrayAdapter<Place> {

    public PlaceAdapter (Context context, ArrayList<Place> places) {
        super(context, 0, places);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link Word} object located at this position in the list
        final Place currentPlace = getItem(position);

        TextView textView = (TextView) listItemView.findViewById(R.id.name_text_view);
        textView.setText(currentPlace.getSightName());

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);
        imageView.setImageResource(currentPlace.getSightImageId());

        final ToggleButton toggleButton_main = (ToggleButton) listItemView.findViewById(R.id.fav_togglebox_main);
        toggleButton_main.setChecked(false);
        toggleButton_main.setBackgroundResource(R.drawable.ic_favorite_border_white_24dp);
        final boolean isAlreadyChecked = currentPlace.getSightFavorite();
        if(isAlreadyChecked){
            toggleButton_main.setChecked(true);
            toggleButton_main.setBackgroundResource(R.drawable.ic_favorite_white_24dp);
        } else {
            toggleButton_main.setChecked(false);
            toggleButton_main.setBackgroundResource(R.drawable.ic_favorite_border_white_24dp);
        }

        toggleButton_main.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {

                    if (isAlreadyChecked){
                        currentPlace.setSightFavorite(false);
                        toggleButton_main.setBackgroundResource(R.drawable.ic_favorite_border_white_24dp);
                        return;
                    }
                    currentPlace.setSightFavorite(true);
                    toggleButton_main.setBackgroundResource(R.drawable.ic_favorite_white_24dp);
                } else {
                    currentPlace.setSightFavorite(false);
                    toggleButton_main.setBackgroundResource(R.drawable.ic_favorite_border_white_24dp);
                }
            }
        });

        return listItemView;
    }
}
