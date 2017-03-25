package com.wow.wowmeet.screens.main.map;

import com.wow.wowmeet.data.main.MainRepository;
import com.wow.wowmeet.models.Event;
import com.wow.wowmeet.models.Location;
import com.wow.wowmeet.models.User;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mahmutkaraca on 3/25/17.
 */

public class MapPresenter implements MapContract.Presenter {

    public static ArrayList<Event> events = new ArrayList<>();

    private MapContract.View view;
    private MainRepository mainRepository;

    public MapPresenter(MapContract.View view) {
        this.view = view;
        this.mainRepository = new MainRepository();
    }

    @Override
    public void start() {
        mainRepository.getEvents().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Event>>() {
                    @Override
                    public void onSuccess(List<Event> value) {
                        view.showEvents(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onError(e);
                    }
                });
    }

    @Override
    public void stop() {
        //TODO stops
    }

    @Override
    public void requestEventRefresh() {
        User u = new User.UserBuilder()
                .setUserId("1")
                .setName("KaracaSoft")
                .setEmail("coolcocuk@cool.com")
                .setPassword("asdf")
                .setToken("token")
                .createUser();

        Location loc = new Location("asdf", 23.59, 23.59);
        Event e = new Event(loc, "denem", u);
        events.add(e);

        Location loc2 = new Location("qwer", 30.59, 30.59);
        Event e2 = new Event(loc2, "deneme", u);
        events.add(e2);

        view.showEvents(events);
    }
}
