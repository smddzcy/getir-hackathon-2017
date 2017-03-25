package com.wow.wowmeet.screens.main.drawer;

/**
 * Created by mahmutkaraca on 3/25/17.
 */

public class DrawerPresenter implements DrawerContract.Presenter {

    private DrawerContract.View view;

    public DrawerPresenter(DrawerContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void onItemClick(int position, String action) {

    }
}
