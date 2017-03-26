package com.wow.wowmeet.utils.googleapi;

import android.content.Context;

import com.google.android.gms.location.places.Places;

/**
 * Created by mahmutkaraca on 3/25/17.
 */

public class GooglePlacesAPIWrapper {

    private Context context;
    private GoogleApiProvider googleApiProvider;

    public GooglePlacesAPIWrapper(Context context, GoogleApiProvider.OnProviderConnectedListener onProviderConnectedListener,
                                  GoogleApiProvider.OnProviderConnectionFailedListener onProviderConnectionFailedListener) {
        this.context = context;

        googleApiProvider = new GoogleApiProvider(context, onProviderConnectedListener,
                onProviderConnectionFailedListener,
                Places.GEO_DATA_API,
                Places.PLACE_DETECTION_API);

    }

    public void onStart() {
        googleApiProvider.onStart();
    }

    public void onStop() {
        googleApiProvider.onStop();
    }

}
