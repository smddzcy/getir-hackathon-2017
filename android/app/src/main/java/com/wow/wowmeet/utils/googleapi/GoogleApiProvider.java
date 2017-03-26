package com.wow.wowmeet.utils.googleapi;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by mahmutkaraca on 3/25/17.
 */

public class GoogleApiProvider {
    private Context context;
    private GoogleApiClient googleApiClient;

    private boolean connected = false;

    private GoogleApiClient.ConnectionCallbacks callbacks = new GoogleApiClient.ConnectionCallbacks() {
        @Override
        public void onConnected(@Nullable Bundle bundle) {
            connected = true;
            onProviderConnectedListener.onConnected();
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
            Log.e("GoogleApiProvider", connectionResult.getErrorMessage());
            onProviderConnectionFailedListener.onConnectionFailed();
        }
    };

    private GoogleLocationAPIWrapper.WrapperLocationListener wrapperLocationListener;
    private OnProviderConnectedListener onProviderConnectedListener;
    private OnProviderConnectionFailedListener onProviderConnectionFailedListener;

    public GoogleApiProvider(@NonNull Context context, @NonNull OnProviderConnectedListener onProviderConnectedListener,
                             @NonNull OnProviderConnectionFailedListener onProviderConnectionFailedListener,
                             Api... apis) {
        this.context = context;
        this.onProviderConnectedListener = onProviderConnectedListener;
        this.onProviderConnectionFailedListener = onProviderConnectionFailedListener;

        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(callbacks)
                .addOnConnectionFailedListener(onConnectionFailedListener);

        for (Api api : apis) {
            builder.addApi(api);
        }

        this.googleApiClient = builder.build();
    }

    public void onStart() {
        googleApiClient.connect();
    }

    public void onStop() {
        googleApiClient.disconnect();
    }

    public boolean isConnected() {
        return connected;
    }

    public GoogleApiClient getGoogleApiClient() {
        return googleApiClient;
    }

    public interface OnProviderConnectedListener {
        void onConnected();
    }

    public interface OnProviderConnectionFailedListener {
        void onConnectionFailed();
    }
}
