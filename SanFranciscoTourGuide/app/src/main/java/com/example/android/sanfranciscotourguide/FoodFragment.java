package com.example.android.sanfranciscotourguide;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodFragment extends Fragment {



    public FoodFragment() {
        // Required empty public constructor
    }
    final ArrayList<Place> places = new ArrayList<Place>();
    private static Bundle bundle = new Bundle();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.main_list, container, false);

        final ArrayList<Place> places = new ArrayList<Place>();
        places.add(new Place("Aster",R.drawable.aster,"1001 Guerrero St, San Francisco, CA 94110","4158759810","Open All Days 24 hours","http://astersf.com/",false,this.getString(R.string.aster)));
        places.add(new Place("Shizen Vegan Sushi",R.drawable.shizen,"370 14th St, San Francisco, CA 94103","4156785767","Open All Days 9AM - 6PM","https://www.shizensf.com/",false,this.getString(R.string.shizen)));
        places.add(new Place("Rooh",R.drawable.rooh,"5333 Brannan St #150, San Francisco, CA 94107","4155254174","Open All Days 10AM–5PM","http://www.roohsf.com/",false,this.getString(R.string.rooh)));
        places.add(new Place("Tratto",R.drawable.tratto,"501 Geary St, San Francisco, CA 94102","4152920101","Open All Days 10AM–10PM","http://www.tratto-sf.com/",false,this.getString(R.string.tratto)));

        PlaceAdapter placeAdapter = new PlaceAdapter(getActivity(),places);

        ListView listView = (ListView) mainView.findViewById(R.id.list);

        listView.setAdapter(placeAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Place place = places.get(i);
                final ToggleButton toggleButton = (ToggleButton) getActivity().findViewById(R.id.fav_togglebox_main);
                bundle.putBoolean("ToggleButtonState", toggleButton.isChecked());
                toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    boolean isAlreadyChecked = place.getSightFavorite();
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if (isChecked) {
                            if (isAlreadyChecked){
                                place.setSightFavorite(false);
                                toggleButton.setBackgroundResource(R.drawable.ic_favorite_border_white_24dp);
                                return;
                            }
                            place.setSightFavorite(true);
                            toggleButton.setBackgroundResource(R.drawable.ic_favorite_white_24dp);
                        } else {
                            if (isAlreadyChecked){
                                place.setSightFavorite(true);
                                toggleButton.setBackgroundResource(R.drawable.ic_favorite_white_24dp);
                                return;
                            }
                            place.setSightFavorite(false);
                            toggleButton.setBackgroundResource(R.drawable.ic_favorite_border_white_24dp);
                        }

                    }
                });
                Intent itemIntent = new Intent(getActivity(), ItemActivity.class);
                itemIntent.putExtra("sampleObject",place);
                startActivity(itemIntent);
            }
        });

        return mainView;
    }

    @Override
    public void onPause() {
        super.onPause();
        ToggleButton toggleButton = (ToggleButton) getActivity().findViewById(R.id.fav_togglebox_main);
        bundle.putBoolean("ToggleButtonState", toggleButton.isChecked());
    }

    @Override
    public void onResume() {
        super.onResume();
        //   Toast.makeText(getActivity(),"RESUME",Toast.LENGTH_LONG).show();
    }


}
