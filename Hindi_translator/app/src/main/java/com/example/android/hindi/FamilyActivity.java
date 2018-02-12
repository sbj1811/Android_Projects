package com.example.android.hindi;

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
        words.add(new Word("father", "पिता",R.drawable.family_father));
        words.add(new Word("mother", "मां",R.drawable.family_mother));
        words.add(new Word("son", "बेटा",R.drawable.family_son));
        words.add(new Word("daughter", "बेटी",R.drawable.family_daughter));
        words.add(new Word("older brother", "बड़ा भाई",R.drawable.family_older_brother));
        words.add(new Word("younger brother", "छोटा भाई",R.drawable.family_younger_brother));
        words.add(new Word("older sister", "बड़ी बहन",R.drawable.family_older_sister));
        words.add(new Word("younger sister", "छोटी बहन",R.drawable.family_younger_sister));
        words.add(new Word("grandmother ", "दादी मा",R.drawable.family_grandmother));
        words.add(new Word("grandfather", "दादा",R.drawable.family_grandfather));

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_family);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
    }
}
