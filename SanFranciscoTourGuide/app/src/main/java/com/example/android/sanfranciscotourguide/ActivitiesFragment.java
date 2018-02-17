package com.example.android.sanfranciscotourguide;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActivitiesFragment extends Fragment {


    public ActivitiesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.main_list, container, false);

        final ArrayList<Place> places = new ArrayList<Place>();
        places.add(new Place("de Young Museum",R.drawable.deyoung,"de Young Museum, San Francisco, CA","4157503600","Open All Days 24 hours","https://deyoung.famsf.org",false,this.getString(R.string.deyoung_info)));
        places.add(new Place("SF Giants AT&T Park",R.drawable.attpark,"AT&T Park, San Francisco, CA","4159722000","Open All Days 9AM - 6PM","http://www.sfgiants.com",false,this.getString(R.string.attpark_info)));
        places.add(new Place("Big Bus Tours",R.drawable.bigbus,"99 Jefferson Street San Francisco, CA 94133","4154331657","Open All Days 10AM–10PM","http://eng.bigbustours.com/sanfrancisco/home.html",false,this.getString(R.string.bigbus_info)));
        places.add(new Place("Hornblower Cruises",R.drawable.cruise,"Pier 3, On the Embarcadero San Francisco, CA 94111","4157888866","Open All Days 10AM–5PM","http://www.hornblower.com",false,this.getString(R.string.cruise_info)));

        PlaceAdapter placeAdapter = new PlaceAdapter(getActivity(),places);

        ListView listView = (ListView) mainView.findViewById(R.id.list);

        listView.setAdapter(placeAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Place place = places.get(i);
                Intent itemIntent = new Intent(getActivity(), ItemActivity.class);
                itemIntent.putExtra("sampleObject",place);
                startActivity(itemIntent);
            }
        });

        return mainView;
    }

}
