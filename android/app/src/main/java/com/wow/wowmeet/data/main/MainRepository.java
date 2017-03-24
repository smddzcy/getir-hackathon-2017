package com.wow.wowmeet.data.main;

import com.wow.wowmeet.models.Event;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by ergunerdogmus on 25.03.2017.
 */

public class MainRepository implements EventsRepository {

    @Override
    public Single<List<Event>> getEvents() {
        return null;
    }

    @Override
    public Single<String> addEvent(Event event) {
        return null;
    }

    @Override
    public Single<String> removeEvent(Event event) {
        return null;
    }

    @Override
    public Single<Event> getSingleEvent(String eventId) {
        return null;
    }
}
