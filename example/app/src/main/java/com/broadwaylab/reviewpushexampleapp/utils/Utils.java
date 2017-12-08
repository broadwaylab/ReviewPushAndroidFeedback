package com.broadwaylab.reviewpushexampleapp.utils;

import android.content.Context;
import android.location.LocationManager;

/**
 * Created by rodol on 07/12/2017.
 */

public class Utils {
    public static boolean isGPSActivated(Context context) {
        LocationManager locman = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locman.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

}
