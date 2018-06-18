package com.example.android.jokedisplay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeDisplayActivity extends AppCompatActivity {

    private static final String JOKE = "joke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);

        TextView textView = (TextView) findViewById(R.id.joke_textView);

        Intent intent =  getIntent();

        if(intent.hasExtra(JOKE)){
            String joke = intent.getStringExtra(JOKE);
            if (joke != null && joke.length() != 0){
                textView.setText(joke);
            }
        }
    }
}
