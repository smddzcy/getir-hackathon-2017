package com.wow.wowmeet.screens.eventinfo;

import com.wow.wowmeet.base.BasePresenter;
import com.wow.wowmeet.base.BaseView;
import com.wow.wowmeet.models.Event;

/**
 * Created by mahmutkaraca on 3/25/17.
 */

interface EventInfoContract {

    interface View extends BaseView<EventInfoContract.Presenter> {
        void showEventInfo(Event event, boolean isUserJoined);
    }

    interface Presenter extends BasePresenter {
        void onJoinClicked();
    }

}
