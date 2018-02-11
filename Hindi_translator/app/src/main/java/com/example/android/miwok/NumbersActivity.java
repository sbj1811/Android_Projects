package com.example.android.miwok;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("one","एक"));
        words.add(new Word("two","दो"));
        words.add(new Word("three","तीन"));
        words.add(new Word("four","चार"));
        words.add(new Word("five","पांच"));
        words.add(new Word("six","छह"));
        words.add(new Word("seven","सात"));
        words.add(new Word("eight","आठ"));
        words.add(new Word("nine","नौ"));
        words.add(new Word("ten","दस"));


        WordAdapter adapter = new WordAdapter(this, words);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
    }
}
