package com.example.android.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by sjani on 2/21/2018.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    private static final String LOG_TAG = NewsAdapter.class.getSimpleName();

    public NewsAdapter(@NonNull Context context, @NonNull List<News> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView ==  null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent,false);
        }

        final News news = getItem(position);

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.news_image);
        if (news.getNewsThumbnail() != null) {
            Log.e(LOG_TAG, "IMAGE: " + news.getNewsThumbnail());
            Picasso.with(getContext()).load(news.getNewsThumbnail()).into(imageView);
        } else {
            imageView.setImageResource(R.drawable.goldengatebridge);
        }
        TextView titleView = (TextView) listItemView.findViewById(R.id.news_title);
        titleView.setText(news.getNewsHeadline());

        TextView dateView = (TextView) listItemView.findViewById(R.id.news_date);
        String dateObject = news.getNewsDate();
        String date = "";
        try {
            final SimpleDateFormat sdf_date_1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            final Date dateObj = sdf_date_1.parse(dateObject);
            final SimpleDateFormat sdf_date_2 = new SimpleDateFormat("MMM dd,yyyy\nh:mm a");
            date = sdf_date_2.format(dateObj);
        } catch (ParseException p){
            Log.e(LOG_TAG, "Problem parsing date", p);
        }
        dateView.setText(date);


        TextView sectionView = (TextView) listItemView.findViewById(R.id.news_section);
        sectionView.setText(news.getNewsSection());

        TextView typeView = (TextView) listItemView.findViewById(R.id.news_type);
        typeView.setText(news.getNewsType());



        return listItemView;

    }

}
