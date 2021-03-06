package com.wow.wowmeet.screens.main;

import com.wow.wowmeet.base.BasePresenter;
import com.wow.wowmeet.base.BaseView;
import com.wow.wowmeet.models.Event;
import com.wow.wowmeet.partials.dialogs.FilterDialogFragment;

import java.util.List;

/**
 * Created by mahmutkaraca on 3/24/17.
 */

interface MainContract {

    interface View extends BaseView<MainContract.Presenter> {
        void refreshListAndMap(List<Event> arr);

        void goCreateEventActivity();

        void showLoading();

        void hideLoading();
    }

    interface Presenter extends BasePresenter, FilterDialogFragment.OnFilterDialogResultListener {
        void onAddEventClicked();
        void onRefreshListAndMap();
        void onRefreshListAndMap(double lat, double lng, double rad);

        void onLogoutClicked();
        void onProfileClicked();
    }

}
