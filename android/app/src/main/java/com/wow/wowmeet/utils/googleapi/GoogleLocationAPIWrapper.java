package com.wow.wowmeet.utils.googleapi;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


/**
 * Created by mahmutkaraca on 3/25/17.
 */

public class GoogleLocationAPIWrapper {

    private Context context;
    GoogleApiProvider googleApiProvider;

    private boolean requestingLocationUpdates = false;

    private Location lastKnownLocation;

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            lastKnownLocation = location;
            if(wrapperLocationListener != null) {
                wrapperLocationListener.onLocationChanged(location);
            }
        }
    };

    private WrapperLocationListener wrapperLocationListener;
    private GoogleApiProvider.OnProviderConnectedListener onProviderConnectedListener;
    private GoogleApiProvider.OnProviderConnectionFailedListener onProviderConnectionFailedListener;

    public GoogleLocationAPIWrapper(@NonNull Context context, @NonNull GoogleApiProvider.OnProviderConnectedListener onProviderConnectedListener,
                                    @NonNull GoogleApiProvider.OnProviderConnectionFailedListener onProviderConnectionFailedListener) {
        this.context = context;
        this.onProviderConnectedListener = onProviderConnectedListener;
        this.onProviderConnectionFailedListener = onProviderConnectionFailedListener;

        this.googleApiProvider = new GoogleApiProvider(context, onProviderConnectedListener,
                onProviderConnectionFailedListener, LocationServices.API);

    }

    public void onStart() {
        this.googleApiProvider.onStart();
    }

    public void onStop() {
        if(requestingLocationUpdates) {
            stopLocationUpdates();
        }
        this.googleApiProvider.onStop();
    }

    public Location getLastKnownLocation() throws SecurityException {
        if(!googleApiProvider.isConnected()) {
            Log.e("GoogleLocAPIWrapper", "API not connected");
            return null;
        }
        if(lastKnownLocation == null) {
            lastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiProvider.getGoogleApiClient());
            return lastKnownLocation;
        }
        return lastKnownLocation;
    }

    public void startLocationUpdates(LocationRequest request, @Nullable WrapperLocationListener listener) throws SecurityException{
        if(!googleApiProvider.isConnected()) {
            Log.e("GoogleLocAPIWrapper", "API not connected");
            return;
        }
        wrapperLocationListener = listener;
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiProvider.getGoogleApiClient(), request, locationListener);
        requestingLocationUpdates = true;
    }

    public void stopLocationUpdates() {
        if(!googleApiProvider.isConnected()) {
            Log.e("GoogleLocAPIWrapper", "API not connected");
            return;
        }
        if(!requestingLocationUpdates) {
            Log.e("GoogleLocAPIWrapper", "Not requesting location updates");
            return;
        }
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiProvider.getGoogleApiClient(), locationListener);
        requestingLocationUpdates = false;
    }

    public interface WrapperLocationListener {
        void onLocationChanged(Location location);
    }

}
