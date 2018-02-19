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
public class NightlifeFragment extends Fragment {


    public NightlifeFragment() {
        // Required empty public constructor
    }
    final ArrayList<Place> places = new ArrayList<Place>();
    private static Bundle bundle = new Bundle();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.main_list, container, false);

        final ArrayList<Place> places = new ArrayList<Place>();
        places.add(new Place("Bourbon and Branch",R.drawable.bourbonbranch,"501 Jones Street San Francisco, CA 94102","4153461735","Open All Days 24 hours","http://www.bourbonandbranch.com/",false,this.getString(R.string.bourbonbranch)));
        places.add(new Place("1015 Folsom",R.drawable.folsom,"1015 Folsom St, San Francisco, CA 94103","4159911015","Open All Days 9AM - 6PM","http://1015.com/",false,this.getString(R.string.folsom)));
        places.add(new Place("Devil's Acre",R.drawable.devilsccre,"256 Columbus Avenue San Francisco, CA 94133","4157664363","Open All Days 10AM–10PM","http://www.thedevilsacre.com/",false,this.getString(R.string.devilsacre)));
        places.add(new Place("Temple Nightclub",R.drawable.temple,"540 Howard St, San Francisco, CA 94105","4153123668","Open All Days 10AM–5PM","http://templesf.com",false,this.getString(R.string.temple)));

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
