package com.wow.wowmeet.screens.eventinfo;

import com.wow.wowmeet.models.Event;

/**
 * Created by mahmutkaraca on 3/25/17.
 */

public class EventInfoPresenter implements EventInfoContract.Presenter {

    private EventInfoContract.View view;
    private Event event;

    public EventInfoPresenter(EventInfoContract.View view, Event event) {
        this.view = view;
        this.event = event;
    }

    @Override
    public void start() {
        view.showEventInfo(event);
    }

    @Override
    public void stop() {

    }

    @Override
    public void onJoinClicked() {

    }
}
