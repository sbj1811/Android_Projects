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
        places.add(new Place("Golden Gate bridge",R.drawable.goldengatebridge,"Golden Gate Bridge, San Francisco, CA","415-921-5858","Open All Days 24 hours","http://www.goldengatebridge.org/",false));
        places.add(new Place("Alcatraz",R.drawable.alcatraz,"San Francisco, CA 94133","415-561-4900","Open All Days 9AM - 6PM","https://www.nps.gov/alca/index.htm",false));
        places.add(new Place("Pier 39",R.drawable.pier39,"Beach St & The Embarcadero, San Francisco, CA 94133","","Open All Days 10AM–10PM","http://www.pier39.com/",false));
        places.add(new Place("Coit Tower",R.drawable.coittower,"1 Telegraph Hill Blvd, San Francisco, CA 94133","415-249-0995","Open All Days 10AM–5PM","http://sfrecpark.org/destination/telegraph-hill-pioneer-park/coit-tower/",false));

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
