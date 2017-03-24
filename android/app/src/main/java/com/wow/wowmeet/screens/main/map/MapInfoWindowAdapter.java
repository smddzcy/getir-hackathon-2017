package com.wow.wowmeet.screens.main.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.wow.wowmeet.models.Event;

import java.util.ArrayList;

/**
 * Created by mahmutkaraca on 3/25/17.
 */

public class MapInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Context context;
    private ArrayList<Event> events;


    public MapInfoWindowAdapter(Context context, ArrayList<Event> dataSet) {
        this.context = context;
        this.events = dataSet;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        LayoutInflater inflater = LayoutInflater.from(context);

        

        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
