package com.wow.wowmeet.screens.main.map;

import com.wow.wowmeet.data.main.MainRepository;
import com.wow.wowmeet.models.Event;

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
                        view.showError(e.getMessage());
                    }
                });
    }

    @Override
    public void stop() {
        //TODO stops
    }

    @Override
    public void requestEventRefresh() {
        this.start();
    }

    @Override
    public void onEventClick(Event event) {
        view.showEventInfo(event);
    }
}
