package com.example.android.bakingapp.UI.RecipeList;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.bakingapp.Models.Recipe;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.UI.RecipeIngredient.IngredientActivity;
import com.facebook.stetho.Stetho;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String UPDATE_WIDGET = "android.appwidget.action.APPWIDGET_UPDATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListFragment listFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.list_container);

        if(listFragment == null){
            listFragment = ListFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.list_container,listFragment)
                    .commit();
        }

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build()
        );
        final Intent update = new Intent(UPDATE_WIDGET);
        this.sendBroadcast(update);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
