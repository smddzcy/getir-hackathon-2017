package com.wow.wowmeet.screens.main.list;

import com.wow.wowmeet.data.main.MainRepository;
import com.wow.wowmeet.models.Event;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mahmutkaraca on 3/24/17.
 */

public class ListPresenter implements ListContract.Presenter {

    private ListContract.View view;
    private MainRepository mainRepository;

    ListPresenter(ListContract.View view) {
        this.view = view;
        this.mainRepository = new MainRepository();
    }

    @Override
    public void start() {
        mainRepository.getEvents()
                .subscribeOn(Schedulers.io())
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

    }

    @Override
    public void onSwipeRefresh() {

    }
}
