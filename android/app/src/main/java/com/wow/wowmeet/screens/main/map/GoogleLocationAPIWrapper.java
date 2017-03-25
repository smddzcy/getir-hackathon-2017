package com.wow.wowmeet.screens.main.map;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


/**
 * Created by mahmutkaraca on 3/25/17.
 */

public class GoogleLocationAPIWrapper {

    private Context context;
    private GoogleApiClient googleApiClient;

    private boolean connected = false;
    private boolean requestingLocationUpdates = false;

    private Location lastKnownLocation;

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            lastKnownLocation = location;
            wrapperLocationListener.onLocationChanged(location);
        }
    };

    private GoogleApiClient.ConnectionCallbacks callbacks = new GoogleApiClient.ConnectionCallbacks() {
        @Override
        public void onConnected(@Nullable Bundle bundle) {
            connected = true;
            onWrapperConnectedListener.onConnected();
        }

        @Override
        public void onConnectionSuspended(int i) {
            connected = false;
        }
    };

    private GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener =new GoogleApiClient.OnConnectionFailedListener() {
        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            connected = false;
            onWrapperConnectionFailedListener.onConnectionFailed();
        }
    };

    private WrapperLocationListener wrapperLocationListener;
    private OnWrapperConnectedListener onWrapperConnectedListener;
    private OnWrapperConnectionFailedListener onWrapperConnectionFailedListener;

    public GoogleLocationAPIWrapper(@NonNull Context context, @NonNull OnWrapperConnectedListener onWrapperConnectedListener,
                                    @NonNull OnWrapperConnectionFailedListener onWrapperConnectionFailedListener) {
        this.context = context;
        this.onWrapperConnectedListener = onWrapperConnectedListener;
        this.onWrapperConnectionFailedListener = onWrapperConnectionFailedListener;

        this.googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(callbacks)
                .addOnConnectionFailedListener(onConnectionFailedListener)
                .addApi(LocationServices.API)
                .build();
    }

    public void onStart() {
        googleApiClient.connect();
    }

    public void onStop() {
        if(requestingLocationUpdates) {
            stopLocationUpdates();
        }
        googleApiClient.disconnect();
    }

    public Location getLastKnownLocation() throws SecurityException {
        if(!connected) {
            Log.e("GoogleLocAPIWrapper", "API not connected");
            return null;
        }
        if(lastKnownLocation == null) {
            lastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            return lastKnownLocation;
        }
        return lastKnownLocation;
    }

    public void startLocationUpdates(LocationRequest request, @NonNull WrapperLocationListener listener) throws SecurityException{
        if(!connected) {
            Log.e("GoogleLocAPIWrapper", "API not connected");
            return;
        }
        wrapperLocationListener = listener;
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, request, locationListener);
        requestingLocationUpdates = true;
    }

    public void stopLocationUpdates() {
        if(!connected) {
            Log.e("GoogleLocAPIWrapper", "API not connected");
            return;
        }
        if(!requestingLocationUpdates) {
            Log.e("GoogleLocAPIWrapper", "Not requesting location updates");
            return;
        }
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, locationListener);
        requestingLocationUpdates = false;
    }

    public interface WrapperLocationListener {
        void onLocationChanged(Location location);
    }

    public interface OnWrapperConnectedListener {
        void onConnected();
    }

    public interface OnWrapperConnectionFailedListener {
        void onConnectionFailed();
    }

}
