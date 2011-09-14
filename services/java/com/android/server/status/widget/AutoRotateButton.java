package com.android.server.status.widget;

import com.android.internal.R;
import com.android.server.status.widget.PowerButton;

import android.content.Context;
import android.provider.Settings;

public class AutoRotateButton extends PowerButton {

    Context mContext;

    static AutoRotateButton ownButton = null;

    @Override
    public void toggleState(Context context) {
        if(getOrientationState(context) == 0) {
            Settings.System.putInt(
                    context.getContentResolver(),
                    Settings.System.ACCELEROMETER_ROTATION, 1);
        } else {
            Settings.System.putInt(
                    context.getContentResolver(),
                    Settings.System.ACCELEROMETER_ROTATION, 0);
        }           }

    @Override
    public void updateState(Context context) {
        mContext = context;

        if (getOrientationState(context) == 1) {
            currentIcon = R.drawable.stat_orientation_on;
            currentState = PowerButton.STATE_ENABLED;
        } else {
            currentIcon = R.drawable.stat_orientation_off;
            currentState = PowerButton.STATE_DISABLED;
        }
    }

    public static int getOrientationState(Context context) {
        return Settings.System.getInt(
                context.getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION, 0);
    }

    public static AutoRotateButton getInstance() {
        if (ownButton == null) ownButton = new AutoRotateButton();
        return ownButton;
    }

    @Override
    void initButton(int position) {
    }
}
