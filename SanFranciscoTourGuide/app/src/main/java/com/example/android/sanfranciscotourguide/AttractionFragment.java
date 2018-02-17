package com.example.android.sanfranciscotourguide;


import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
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
public class AttractionFragment extends Fragment {


    public AttractionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.main_list, container, false);

        final ArrayList<Place> places = new ArrayList<Place>();
        places.add(new Place("Golden Gate bridge",R.drawable.goldengatebridge,"Golden Gate Bridge, San Francisco, CA","4159215858","Open All Days 24 hours","http://www.goldengatebridge.org/",false,this.getString(R.string.goldengate_info)));
        places.add(new Place("Alcatraz",R.drawable.alcatraz,"Alcatraz Island, San Francisco, CA","4155614900","Open All Days 9AM - 6PM","https://www.nps.gov/alca/index.htm",false,this.getString(R.string.alcatraz_info)));
        places.add(new Place("Pier 39",R.drawable.pier39,"Pier 39, San Francisco, CA","","Open All Days 10AM–10PM","http://www.pier39.com/",false,this.getString(R.string.pier_info)));
        places.add(new Place("Coit Tower",R.drawable.coittower,"Coit Tower, San Francisco, CA","4152490995","Open All Days 10AM–5PM","http://sfrecpark.org/destination/telegraph-hill-pioneer-park/coit-tower/",false,this.getString(R.string.coit_info)));

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
