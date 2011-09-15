package com.android.server.status.widget;

import com.android.internal.R;
import com.android.server.status.widget.PowerButton;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.util.Log;

public class GPSButton extends PowerButton {

    Context mContext;

    static GPSButton ownButton;

    public void updateState(Context context) {
        mContext = context;
        Log.i("GPSButton", "Update State");
        if(getGpsState(context)) {
            currentIcon = com.android.internal.R.drawable.stat_gps_on;
            currentState = STATE_ENABLED;
        } else {
            currentIcon = com.android.internal.R.drawable.stat_gps_off;
            currentState = STATE_DISABLED;
        }
    }

    public void callSettings(Context context) {
        Intent intent = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void toggleState(Context context) {
        Log.i("GPSButton", "ToggleState");
        ContentResolver resolver = context.getContentResolver();
        boolean enabled = getGpsState(context);
        Settings.Secure.setLocationProviderEnabled(resolver,
                LocationManager.GPS_PROVIDER, !enabled);
    }

    private static boolean getGpsState(Context context) {
        ContentResolver resolver = context.getContentResolver();
        return Settings.Secure.isLocationProviderEnabled(resolver,
                LocationManager.GPS_PROVIDER);
    }

    public static GPSButton getInstance() {
        if (ownButton==null) ownButton = new GPSButton();

        return ownButton;
    }

    @Override
    void initButton(int position) {
    }

}
