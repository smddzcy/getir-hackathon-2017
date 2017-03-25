package com.wow.wowmeet.screens.main;

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

    public MainPresenter(MainContract.View view) {
        this.view = view;
        this.mainRepository = new MainRepository();
    }

    @Override
    public void start() {
        onRefreshListAndMap();
    }

    @Override
    public void stop() {

    }

    @Override
    public void onAddEventClicked() {

    }

    @Override
    public void onRefreshListAndMap() {
        mainRepository.getEvents().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Event>>() {
                    @Override
                    public void onSuccess(List<Event> value) {
                        view.refreshListAndMap(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.getMessage());
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
