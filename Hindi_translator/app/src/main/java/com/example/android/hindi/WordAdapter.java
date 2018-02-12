package com.example.android.hindi;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.hindi.R;

import java.util.ArrayList;


/**
 * Created by sjani on 2/11/2018.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    private int mColorResourseID;

    public WordAdapter(Activity context, ArrayList<Word> word, int colorResourseID){

        super(context, 0 , word);
        mColorResourseID = colorResourseID;
    }

    /**
     * Method to get a view
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Word currentWord = getItem(position);

        TextView hindiTextView = (TextView) listItemView.findViewById(R.id.hindi_text_view);
        hindiTextView.setText(currentWord.getHindiTranslation());

        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
        defaultTextView.setText(currentWord.getDefaultTranslation());
        ImageView defaultimageView = (ImageView) listItemView.findViewById(R.id.image_view);
        defaultimageView.setImageResource(currentWord.getImageResourceId());

        // Set the theme color for the list item
        View textContainer = listItemView.findViewById(R.id.text_list_view);
        // Find the color that the resource ID maps to
        int color = ContextCompat.getColor(getContext(), mColorResourseID);
        // Set the background color of the text container View
        textContainer.setBackgroundColor(color);


//        if (currentWord.hasImage()) {
//            defaultimageView.setImageResource(currentWord.getImageResourceId());
//            defaultTextView.setVisibility(View.VISIBLE);
//        } else {
//            defaultTextView.setVisibility(View.GONE);
//        }

        return listItemView;
    }
}
