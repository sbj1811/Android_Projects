package com.example.android.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
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

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder> {

    private static final String LOG_TAG = NewsAdapter.class.getSimpleName();
    private List<News> NewsList;
    private final OnItemClickListener mClickHandlar;
    private Context mContext;

    public NewsAdapter(List<News> newsList,OnItemClickListener clickHandler) {
        NewsList = newsList;
        mClickHandlar = clickHandler;
    }

    public interface OnItemClickListener  {
        void onClick(News news);
    }

    @Override
    public NewsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View newsView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        NewsAdapterViewHolder newsAdapterViewHolder = new NewsAdapterViewHolder(newsView);
        return newsAdapterViewHolder;
    }

    @Override
    public void onBindViewHolder(NewsAdapterViewHolder holder, int position) {
        News news = NewsList.get(position);
        Picasso.with(mContext).load(news.getNewsThumbnail()).into(holder.imageView);
        holder.titleView.setText(news.getNewsHeadline());

        String dateObject = news.getNewsDate();
        String date = "";
        try {
            final SimpleDateFormat sdf_date_1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            final Date dateObj = sdf_date_1.parse(dateObject);
            final SimpleDateFormat sdf_date_2 = new SimpleDateFormat("MMM dd,yyyy\nh:mm a");
            date = sdf_date_2.format(dateObj);
        } catch (ParseException p) {
            Log.e(LOG_TAG, "Problem parsing date", p);
        }
        holder.dateView.setText(date);

        holder.sectionView.setText(news.getNewsSection());
        holder.typeView.setText(news.getNewsType());
        holder.bind(news,mClickHandlar);
    }

    @Override
    public int getItemCount() {
        return NewsList.size();
    }

    public class NewsAdapterViewHolder extends RecyclerView.ViewHolder {

        public final ImageView imageView;
        public final TextView titleView;
        public final TextView dateView;
        public final TextView sectionView;
        public final TextView typeView;

        public NewsAdapterViewHolder(View view){
            super(view);
            imageView = (ImageView) view.findViewById(R.id.news_image);
            titleView = (TextView) view.findViewById(R.id.news_title);
            dateView = (TextView) view.findViewById(R.id.news_date);
            sectionView = (TextView) view.findViewById(R.id.news_section);
            typeView = (TextView) view.findViewById(R.id.news_type);
        }

        public void bind (final News news, final OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(news);
                }
            });
        }



    }

    public void addAll(List<News> newsList){
        NewsList = newsList;
        notifyDataSetChanged();
    }

    public void clear(){
        NewsList.clear();
        notifyDataSetChanged();
    }

}
