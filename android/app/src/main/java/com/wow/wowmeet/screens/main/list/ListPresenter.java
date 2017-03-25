package com.wow.wowmeet.screens.main.list;

/**
 * Created by mahmutkaraca on 3/24/17.
 */

public class ListPresenter implements ListContract.Presenter {

    private ListContract.View view;

    ListPresenter(ListContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void onSwipeRefresh() {

    }
}
