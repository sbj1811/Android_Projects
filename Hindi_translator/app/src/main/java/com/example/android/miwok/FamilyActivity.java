package com.example.android.miwok;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("father", "पिता"));
        words.add(new Word("mother", "मां"));
        words.add(new Word("son", "बेटा"));
        words.add(new Word("daughter", "बेटी"));
        words.add(new Word("older brother", "बड़ा भाई"));
        words.add(new Word("younger brother", "छोटा भाई"));
        words.add(new Word("older sister", "बड़ी बहन"));
        words.add(new Word("younger sister", "छोटी बहन"));
        words.add(new Word("grandmother ", "दादी मा"));
        words.add(new Word("grandfather", "दादा"));

        WordAdapter adapter = new WordAdapter(this, words);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
    }
}
