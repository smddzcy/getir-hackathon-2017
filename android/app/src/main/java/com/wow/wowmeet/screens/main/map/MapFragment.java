package com.wow.wowmeet.screens.main.map;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wow.wowmeet.R;
import com.wow.wowmeet.models.Event;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends SupportMapFragment implements MapContract.View {

    MapContract.Presenter presenter;

    MapInfoWindowAdapter infoWindowAdapter;

    boolean mapReady = false;
    GoogleMap map;

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
        View v = inflater.inflate(R.layout.info_window_map, container, false);
        infoWindowAdapter = new MapInfoWindowAdapter(v);

        MapPresenter presenter = new MapPresenter(this);
        setPresenter(presenter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                map.setInfoWindowAdapter(infoWindowAdapter);
                mapReady = true;
                presenter.requestEventRefresh();
            }
        });
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
        if(mapReady && map != null) {
            for (Event e : events) {
                map.addMarker(new MarkerOptions()
                    .position(new LatLng(e.getLocation().getLatitude(), e.getLocation().getLongtitude())))
                    .setTag(e);
            }
        } else {
            //TODO EMPTY ELSE?
        }


    }
}
