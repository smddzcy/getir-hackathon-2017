package com.wow.wowmeet.screens.main.map;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wow.wowmeet.R;
import com.wow.wowmeet.models.Event;
import com.wow.wowmeet.screens.eventinfo.EventInfoActivity;

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

        presenter.start();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                initializeMap(googleMap);
            }
        });
    }

    private void initializeMap(GoogleMap googleMap){
        map = googleMap;
        LatLng mainCoordinates = new LatLng(41.0728162, 29.0089026); //TODO DYNAMIC TAKE
        map.setInfoWindowAdapter(infoWindowAdapter);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mainCoordinates, 12.0f));
        mapReady = true;
        presenter.requestEventRefresh();

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                presenter.onEventClick((Event) marker.getTag());
            }
        });
    }


    @Override
    public void setPresenter(MapContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showError(String e) {

    }

    @Override
    public void showEventInfo(Event event) {
        Intent i = new Intent(getActivity(), EventInfoActivity.class);
        i.putExtra(EventInfoActivity.EXTRA_EVENT, event);
        startActivity(i);
    }

    @Override
    public void showEvents(List<Event> events) {
        if(mapReady && map != null) {
            for (Event e : events) {
                map.addMarker(new MarkerOptions()
                    .position(new LatLng(e.getLocation().getLatitude(), e.getLocation().getLongitude())))
                    .setTag(e);
            }
        } else {
            //TODO EMPTY ELSE?
        }


    }
}
