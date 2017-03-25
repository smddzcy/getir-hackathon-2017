package com.wow.wowmeet.screens.main.map;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

/**
 * Created by mahmutkaraca on 3/25/17.
 */

public class PermissionChecker {

    public static final int LOCATION_PERMISSION_REQUEST_CODE = 0x0100;

    public static final String[] locationPermissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    public static boolean checkLocationPermission(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // TODO tatlı dille permission isteyelim.(bkz. Valla bişey yapmicaz. Nolur verin şu izni.)
            ActivityCompat.requestPermissions((Activity) context, locationPermissions, LOCATION_PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }

    public static boolean onRequestPermissionResult(Context context, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if(!(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                checkLocationPermission(context);
                return false;
            }
            return true;
        }
        return false;
    }

}
