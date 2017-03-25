package com.wow.wowmeet.data.main;

import com.wow.wowmeet.models.Event;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by ergunerdogmus on 24.03.2017.
 */

public interface EventsRepository {

    Single<List<Event>> getEvents();

    Single<String> addEvent(Event event);

    Single<List<Event>> getEvents(int lat, int lng, int radius);

    Single<String> removeEvent(Event event);

    Single<Event> getSingleEvent(String eventId);

}
