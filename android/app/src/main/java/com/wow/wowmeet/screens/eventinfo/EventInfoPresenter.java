package com.wow.wowmeet.screens.eventinfo;

import com.wow.wowmeet.models.Event;
import com.wow.wowmeet.models.User;

import java.util.List;

/**
 * Created by mahmutkaraca on 3/25/17.
 */

public class EventInfoPresenter implements EventInfoContract.Presenter {

    private EventInfoContract.View view;
    private Event event;
    private User user;

    public EventInfoPresenter(EventInfoContract.View view, User user, Event event) {
        this.view = view;
        this.event = event;
        this.user = user;
    }

    @Override
    public void start() {
        boolean isUserJoined = isUserJoined();
        view.showEventInfo(event, isUserJoined);
    }

    @Override
    public void stop() {

    }

    @Override
    public void onJoinClicked() {

    }

    private boolean isUserJoined() {
        List<User> joinedUsers = event.getUsers();
        return joinedUsers.contains(user);
    }
}
