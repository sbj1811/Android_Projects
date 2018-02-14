package com.example.android.sanfranciscotourguide;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodFragment extends Fragment {



    public FoodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.main_list, container, false);

        final ArrayList<Place> places = new ArrayList<Place>();
        places.add(new Place("Golden Gate bridge",R.drawable.goldengatebridge));
        places.add(new Place("Alcatraz",R.drawable.alcatraz));
        places.add(new Place("Pier 39",R.drawable.pier39));
        places.add(new Place("Coit Tower",R.drawable.coittower));

        PlaceAdapter placeAdapter = new PlaceAdapter(getActivity(),places);

        ListView listView = (ListView) mainView.findViewById(R.id.list);

        listView.setAdapter(placeAdapter);


        return mainView;
    }

}
