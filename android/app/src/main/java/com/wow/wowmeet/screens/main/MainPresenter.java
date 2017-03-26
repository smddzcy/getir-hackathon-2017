package com.wow.wowmeet.screens.main;

import android.support.annotation.Nullable;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.wow.wowmeet.data.main.MainRepository;
import com.wow.wowmeet.exceptions.BaseException;
import com.wow.wowmeet.models.Event;
import com.wow.wowmeet.models.Location;
import com.wow.wowmeet.models.Type;

import java.util.Calendar;
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

    private double lastLat = -1;
    private double lastLong = -1;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        this.mainRepository = new MainRepository();
    }

    @Override
    public void start() {
        LatLng mainCoordinates = Location.getDefaultLocation(); //TODO DYNAMIC TAKE
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
    public void onRefreshListAndMap() {
        if(lastLat != -1 || lastLong != -1) {
            onRefreshListAndMap(lastLat, lastLong, radius);
        }
    }

    @Override
    public void onRefreshListAndMap(double lat, double lng, double rad) {
        view.showLoading();
        lastLat = lat;
        lastLong = lng;
        radius = rad;
        mainRepository.getEvents(lat, lng, rad).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Event>>() {
                    @Override
                    public void onSuccess(List<Event> value) {
                        view.hideLoading();
                        view.refreshListAndMap(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(e instanceof BaseException) {
                            if(((BaseException)e).isUseResource()) {
                                view.showError(((BaseException) e).getErrorMessageResource());
                            }else {
                                view.showError(((BaseException) e).getErrorMessage());
                            }
                        } else {
                            view.showError(e.getMessage());
                        }
                        view.hideLoading();
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

    @Override
    public void onFilterDialogResult(@Nullable Calendar startDate,
                                     @Nullable Calendar endDate,
                                     @Nullable Place place,
                                     int radius,
                                     @Nullable Type type) {
        if(place != null){
            LatLng latLng = place.getLatLng();
            double lat = latLng.latitude;
            double lng = latLng.longitude;

            this.onRefreshListAndMap(lat, lng, radius);
        }
    }
}
