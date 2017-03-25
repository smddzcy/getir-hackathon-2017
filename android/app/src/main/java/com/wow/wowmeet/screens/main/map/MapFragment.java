package com.wow.wowmeet.screens.main.map;


import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wow.wowmeet.R;
import com.wow.wowmeet.models.Event;
import com.wow.wowmeet.screens.eventinfo.EventInfoActivity;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends SupportMapFragment implements MapContract.View {

    private GoogleLocationAPIWrapper.WrapperLocationListener wrapperLocationListener = new GoogleLocationAPIWrapper.WrapperLocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            presenter.requestEventRefresh(location.getLatitude(), location.getLongitude(), 2);
        }
    };

    MapContract.Presenter presenter;

    MapInfoWindowAdapter infoWindowAdapter;

    private OnRefreshListRequestedListener onRefreshListRequestedListener;

    boolean mapReady = false;
    GoogleMap map;
    GoogleLocationAPIWrapper apiWrapper;

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();

        return fragment;
    }

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.info_window_map, container, false);
        infoWindowAdapter = new MapInfoWindowAdapter(v);

        final MapPresenter presenter = new MapPresenter(this);
        setPresenter(presenter);

        apiWrapper = new GoogleLocationAPIWrapper(getActivity(), new GoogleLocationAPIWrapper.OnWrapperConnectedListener() {
            @Override
            public void onConnected() {
                if(PermissionChecker.checkLocationPermission(getActivity(), false)) {
                    Location lastLocation = apiWrapper.getLastKnownLocation();

                    if(lastLocation != null)
                        presenter.requestEventRefresh(lastLocation.getLatitude(), lastLocation.getLongitude(), 2);

                    requestLocationUpdates();
                }
            }
        }, new GoogleLocationAPIWrapper.OnWrapperConnectionFailedListener() {
            @Override
            public void onConnectionFailed() {
                //TODO error message about how bad the internet is...
            }
        });

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                initializeMap(googleMap);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        apiWrapper.onStart();
        presenter.start();
    }

    private void requestLocationUpdates() {
        LocationRequest request = LocationRequest.create();
        //request.setSmallestDisplacement(2);
        apiWrapper.startLocationUpdates(request, wrapperLocationListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        apiWrapper.onStop();
    }


    private void initializeMap(GoogleMap googleMap) {
        map = googleMap;
        LatLng mainCoordinates = new LatLng(41.0728162, 29.0089026); //TODO DYNAMIC TAKE
        map.setInfoWindowAdapter(infoWindowAdapter);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mainCoordinates, 12.0f));
        mapReady = true;
        presenter.requestEventRefresh(mainCoordinates.latitude, mainCoordinates.longitude, 1.0);

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                presenter.onEventClick((Event) marker.getTag());
            }
        });

        enableLocationFeatures();

    }

    private void enableLocationFeatures() {
        if(PermissionChecker.checkLocationPermission(getActivity(), true)) {
            map.setMyLocationEnabled(true);
            requestLocationUpdates();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionChecker.onRequestPermissionResult(requestCode, permissions, grantResults);
        if(requestCode == PermissionChecker.LOCATION_PERMISSION_REQUEST_CODE) {
            enableLocationFeatures();
        }
    }

    @Override
    public void setPresenter(MapContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showError(String e) {

    }

    @Override
    public void showEventInfo(Event event) {
        Intent i = new Intent(getActivity(), EventInfoActivity.class);
        i.putExtra(EventInfoActivity.EXTRA_EVENT, event);
        startActivity(i);
    }

    @Override
    public void showEvents(List<Event> events) {
        if(mapReady && map != null) {
            for (Event e : events) {
                map.addMarker(new MarkerOptions()
                    .position(new LatLng(e.getLocation().getLatitude(), e.getLocation().getLongitude())))
                    .setTag(e);
            }
        } else {
            //TODO EMPTY ELSE?
        }


    }

    @Override
    public void onPermissionRequestResolved(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        this.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void setOnRefreshListRequestedListener(OnRefreshListRequestedListener onRefreshListRequestedListener) {
        this.onRefreshListRequestedListener = onRefreshListRequestedListener;
    }

    public interface OnRefreshListRequestedListener {
        void onRefreshListRequested();
    }
}
