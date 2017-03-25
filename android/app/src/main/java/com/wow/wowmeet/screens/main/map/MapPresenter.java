package com.wow.wowmeet.screens.main.map;

import com.wow.wowmeet.models.Event;

import java.util.ArrayList;

/**
 * Created by mahmutkaraca on 3/25/17.
 */

public class MapPresenter implements MapContract.Presenter {

    public static ArrayList<Event> events = new ArrayList<>();

    private MapContract.View view;

    public MapPresenter(MapContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        //TODO stops
    }

    @Override
    public void requestEventRefresh() {
        this.start();
    }
}
