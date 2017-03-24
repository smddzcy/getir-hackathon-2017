package com.wow.wowmeet.screens.main.map;

import com.wow.wowmeet.BasePresenter;
import com.wow.wowmeet.BaseView;
import com.wow.wowmeet.models.Event;

import java.util.List;

/**
 * Created by ergunerdogmus on 24.03.2017.
 */

interface MapContract {

    interface View extends BaseView<MapContract.Presenter>{
        void showEventInfo(Event event);

        void showEvents(List<Event> events);
    }

    interface Presenter extends BasePresenter{
        void requestEventRefresh();
    }
}
