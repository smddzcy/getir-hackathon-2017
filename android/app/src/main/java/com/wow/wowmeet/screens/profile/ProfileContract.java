package com.wow.wowmeet.screens.profile;

import com.wow.wowmeet.BasePresenter;
import com.wow.wowmeet.BaseView;
import com.wow.wowmeet.models.Event;

import java.util.List;

/**
 * Created by ergunerdogmus on 25.03.2017.
 */

public interface ProfileContract {

    interface View extends BaseView<ProfileContract.Presenter>{
        void showPastEvents(List<Event> events);

        void showNumberOfEventsAttended();
    }

    interface Presenter extends BasePresenter{

    }
}
