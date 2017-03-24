package com.wow.wowmeet.screens.main.map;

import com.wow.wowmeet.models.Event;
import com.wow.wowmeet.models.Location;
import com.wow.wowmeet.models.User;

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

    }

    @Override
    public void requestEventRefresh() {
        User u = new User.UserBuilder()
                .setUserId("1")
                .setUsername("KaracaSoft")
                .setEmail("coolcocuk@cool.com")
                .setPassword("asdf")
                .setToken("token")
                .createUser();

        Location loc = new Location("asdf", 23.59, 23.59);
        Event e = new Event(u, loc);
        events.add(e);

        Location loc2 = new Location("qwer", 30.59, 30.59);
        Event e2 = new Event(u, loc2);
        events.add(e2);

        view.showEvents(events);
    }
}
