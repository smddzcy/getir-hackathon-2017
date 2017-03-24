package com.wow.wowmeet.screens.main.map;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wow.wowmeet.R;
import com.wow.wowmeet.models.Event;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements MapContract.View {

    MapContract.Presenter presenter;

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();

        return fragment;
    }

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);


        return rootView;
    }

    @Override
    public void setPresenter(MapContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void showEventInfo(Event event) {

    }

    @Override
    public void showEvents(List<Event> events) {

    }
}
