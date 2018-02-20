package com.example.android.booklisting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by sjani on 2/19/2018.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    private static final String LOG_TAG = BookAdapter.class.getSimpleName();


    public BookAdapter(@NonNull Context context, @NonNull List<Book> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        Log.e(LOG_TAG, "START : getview");
        final Book book = getItem(position);

        ImageView bookImage = (ImageView) listItemView.findViewById(R.id.book_image);
        Picasso.with(getContext()).load(book.getBookImage()).into(bookImage);

        TextView titleView = (TextView) listItemView.findViewById(R.id.book_title);
        titleView.setText(book.getBookTitle());


        TextView authorView = (TextView) listItemView.findViewById(R.id.author_name);
        authorView.setText(book.getBookAuthor());

        TextView publisherView = (TextView) listItemView.findViewById(R.id.publisher);
        publisherView.setText(book.getBookPublisher());

        TextView priceView = (TextView) listItemView.findViewById(R.id.price);
        priceView.setText(book.getBookPrice());

        return listItemView;

    }
}
