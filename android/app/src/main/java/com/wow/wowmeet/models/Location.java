package com.wow.wowmeet.models;

/**
 * Created by ergunerdogmus on 25.03.2017.
 */

public class Location {
    String locationName;
    double lat, lang;

    public Location(String locationName, double lat, double lang) {
        this.locationName = locationName;
        this.lat = lat;
        this.lang = lang;
    }
}
