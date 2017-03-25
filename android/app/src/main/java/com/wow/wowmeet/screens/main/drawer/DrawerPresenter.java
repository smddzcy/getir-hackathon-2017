package com.wow.wowmeet.screens.main.drawer;

/**
 * Created by mahmutkaraca on 3/25/17.
 */

public class DrawerPresenter implements DrawerContract.Presenter {

    private DrawerContract.View view;
    private DrawerPreferences drawerPreferences;

    public DrawerPresenter(DrawerContract.View view, DrawerPreferences drawerPreferences) {
        this.view = view;
        this.drawerPreferences = drawerPreferences;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void onItemClick(int position, String action) {
        drawerPreferences.removeUserPrefs();
        view.goLogin();
    }
}
