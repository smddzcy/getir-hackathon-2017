package com.wow.wowmeet.models;

/**
 * Created by ergunerdogmus on 24.03.2017.
 */

public class Event {
    String eventId;
    User user;
    Location location;


    public Event(User user, Location location) {
        this.user = user;
        this.location = location;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
