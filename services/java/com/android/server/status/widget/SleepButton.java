package com.android.server.status.widget;

import com.android.internal.R;
import com.android.server.status.widget.PowerButton;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;

public class SleepButton extends PowerButton {

    Context mContext;

    static SleepButton ownButton;

    public void updateState(Context context) {

        mContext = context;

        currentIcon = com.android.internal.R.drawable.stat_sleep;
        currentState = STATE_DISABLED;
    }

    public void callSettings(Context context) {
        // FIXME: CMParts ?
        Intent intent = new Intent("android.settings.DISPLAY_SETTINGS");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void toggleState(Context context) {
        PowerManager mPowerManager = (PowerManager)context.getSystemService(
                          Context.POWER_SERVICE);
        mPowerManager.goToSleep(SystemClock.uptimeMillis() + 1);
    }

    public static SleepButton getInstance() {
        if (ownButton==null) ownButton = new SleepButton();

        return ownButton;
    }

    @Override
    void initButton(int position) {
    }

}
