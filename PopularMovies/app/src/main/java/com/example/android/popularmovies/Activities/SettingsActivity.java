package com.example.android.popularmovies.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.android.popularmovies.R;

/**
 * Created by sjani on 4/1/2018.
 */

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }

    public static class MoviesPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_layout);

            Preference sortOrder = findPreference(getString(R.string.settings_sort_key));
            bindPreferenceSummaryToValue(sortOrder);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object o) {
            String value = o.toString();
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int preferenceIndex = listPreference.findIndexOfValue(value);
                if (preferenceIndex >= 0) {
                    CharSequence[] lables = listPreference.getEntries();
                    preference.setSummary(lables[preferenceIndex]);
                }
            } else {
                preference.setSummary(value);
            }
            return true;
        }


        private void bindPreferenceSummaryToValue(Preference preference){
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(),"");
            onPreferenceChange(preference, preferenceString);
        }



    }



}
