package com.wow.wowmeet.screens.main.map;

import android.support.annotation.NonNull;

import com.wow.wowmeet.base.BasePresenter;
import com.wow.wowmeet.base.BaseView;
import com.wow.wowmeet.models.Event;

import java.util.List;

/**
 * Created by ergunerdogmus on 24.03.2017.
 */

public interface MapContract {

    interface View extends BaseView<MapContract.Presenter> {
        void showEventInfo(Event event);

        void showEvents(List<Event> events);

        void onPermissionRequestResolved(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
    }

    interface Presenter extends BasePresenter {
        void onGPSTimeout();
        void onEventClick(Event event);
    }
}
