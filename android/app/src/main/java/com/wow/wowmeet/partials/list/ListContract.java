package com.wow.wowmeet.partials.list;

import com.wow.wowmeet.base.BasePresenter;
import com.wow.wowmeet.base.BaseView;
import com.wow.wowmeet.models.Event;

import java.util.List;

/**
 * Created by mahmutkaraca on 3/24/17.
 */

public interface ListContract {

    interface View extends BaseView<ListContract.Presenter> {
        void showEvents(List<Event> events);

        void showEventInfo(Event event);

        void stopRefreshLayoutRefreshingAnimation();
    }

    interface Presenter extends BasePresenter {
        void onEventClicked(Event event);
    }

}
