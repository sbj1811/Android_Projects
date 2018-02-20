package com.example.android.booklisting;

import android.util.Log;

/**
 * Created by sjani on 2/19/2018.
 */

public class Book {

    private String bookTitle;
    private String bookAuthor;
    private String bookPublisher;
    private String bookPrice;
    private String bookImage;
    private String bookUrl;


    public Book(String booktitle, String bookauthor, String bookpublisher, String bookprice, String bookimage, String bookurl) {
        bookTitle = booktitle;
        bookAuthor = bookauthor;
        bookPublisher = bookpublisher;
        bookPrice = bookprice;
        bookImage = bookimage;
        bookUrl = bookurl;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public String getBookPrice() {
        return bookPrice;
    }

    public String getBookImage() {
        return bookImage;
    }

    public String getBookUrl() {
        return bookUrl;
    }
}
