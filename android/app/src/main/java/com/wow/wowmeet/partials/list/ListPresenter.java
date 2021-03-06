package com.wow.wowmeet.partials.list;

import com.wow.wowmeet.models.Event;

/**
 * Created by mahmutkaraca on 3/24/17.
 */

public class ListPresenter implements ListContract.Presenter, ListEventClickListener {

    private ListContract.View view;

    ListPresenter(ListContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void onEventClicked(Event event) {
        view.showEventInfo(event);
    }
}
