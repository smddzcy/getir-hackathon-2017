package com.wow.wowmeet.models;

/**
 * Created by ergunerdogmus on 25.03.2017.
 */

public class Location {
    String name;
    double latitude, longitude;

    public Location(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longtitude) {
        this.longitude = longtitude;
    }

    @Override
    public String toString() {
        return getLatitude() + " " + getLongitude();
    }
}
