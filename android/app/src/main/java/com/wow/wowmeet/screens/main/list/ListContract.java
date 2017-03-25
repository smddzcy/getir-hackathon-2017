package com.wow.wowmeet.screens.main.list;

import com.wow.wowmeet.base.BasePresenter;
import com.wow.wowmeet.base.BaseView;
import com.wow.wowmeet.models.Event;

import java.util.List;

/**
 * Created by mahmutkaraca on 3/24/17.
 */

interface ListContract {

    interface View extends BaseView<ListContract.Presenter> {
        void showEvents(List<Event> events);
    }

    interface Presenter extends BasePresenter {
        void onSwipeRefresh();
    }

}
