package com.wow.wowmeet.screens.main;

import com.google.android.gms.maps.model.LatLng;
import com.wow.wowmeet.data.main.MainRepository;
import com.wow.wowmeet.models.Event;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mahmutkaraca on 3/24/17.
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;
    private MainRepository mainRepository;
    private double radius = 12;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        this.mainRepository = new MainRepository();
    }

    @Override
    public void start() {
        LatLng mainCoordinates = new LatLng(41.0728162, 29.0089026); //TODO DYNAMIC TAKE
        onRefreshListAndMap(mainCoordinates.latitude, mainCoordinates.longitude, radius);
    }

    @Override
    public void stop() {

    }

    @Override
    public void onAddEventClicked() {
        view.goCreateEventActivity();
    }

    @Override
    public void onRefreshListAndMap(double lat, double lng, double rad) {
        mainRepository.getEvents(lat, lng, rad).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Event>>() {
                    @Override
                    public void onSuccess(List<Event> value) {
                        view.refreshListAndMap(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.getMessage());
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void onLogoutClicked() {

    }

    @Override
    public void onProfileClicked() {

    }
}
