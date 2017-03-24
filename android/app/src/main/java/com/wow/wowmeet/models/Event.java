package com.wow.wowmeet.models;

/**
 * Created by ergunerdogmus on 24.03.2017.
 */

public class Event {
    User user;
    String locationName;
    double lat, lang;

    public Event(User user, String locationName, double lat, double lang) {
        this.user = user;
        this.locationName = locationName;
        this.lat = lat;
        this.lang = lang;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLang() {
        return lang;
    }

    public void setLang(double lang) {
        this.lang = lang;
    }
}
