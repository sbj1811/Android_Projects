package com.example.android.myapplication2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.android.myapplication2.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void printToLogs(View view) {
        // Find first menu item TextView and print the text to the logs
        TextView firstTextView = (TextView) findViewById(R.id.menu_item_1);
        String firstText = firstTextView.getText().toString();
        Log.v("MainActivity", firstText);
        // Find second menu item TextView and print the text to the logs
        TextView secondTextView = (TextView) findViewById(R.id.menu_item_2);
        String secondText = secondTextView.getText().toString();
        Log.v("MainActivity", secondText);
        // Find third menu item TextView and print the text to the logs
        TextView thirdTextView = (TextView) findViewById(R.id.menu_item_3);
        String thirdText = thirdTextView.getText().toString();
        Log.v("MainActivity", thirdText);
    }
}