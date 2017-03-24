package com.wow.wowmeet.main;

import com.wow.wowmeet.BasePresenter;
import com.wow.wowmeet.BaseView;

import java.util.ArrayList;

/**
 * Created by mahmutkaraca on 3/24/17.
 */

interface MainContract {

    interface View extends BaseView<MainContract.Presenter> {
        void refreshListAndMap(ArrayList arr);
    }

    interface Presenter extends BasePresenter {
        void onAddEventClicked();
        void onRefreshList();
        void onRefreshMap();

        void onLogoutClicked();
        void onProfileClicked();
    }

}
