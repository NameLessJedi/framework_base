package com.android.server.status.widget;

import com.android.internal.R;
import com.android.server.status.widget.PowerButton;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

public class FlashlightButton extends PowerButton {

    Context mContext;
    static FlashlightButton ownButton;
    private static final String TAG = "FlashlightButton";

    public void updateState(Context context) {
        mContext = context;

        if(getFlashlightEnabled()) {
            currentIcon = com.android.internal.R.drawable.stat_flashlight_on;
        } else {
            currentIcon = com.android.internal.R.drawable.stat_flashlight_off;
            currentState = STATE_DISABLED;
        }
    }

    public void callSettings(Context context) {
        // FIXME: Is there any setting for this ?
    }

    public void toggleState(Context context) {
        boolean bright = Settings.System.getInt(context.getContentResolver(),
                Settings.System.EXPANDED_FLASH_MODE, 0) == 1;
        Intent i = new Intent("net.cactii.flash2.TOGGLE_FLASHLIGHT");
        i.putExtra("bright", bright);
        mContext.sendBroadcast(new Intent("net.cactii.flash2.TOGGLE_FLASHLIGHT"));
    }

    public static FlashlightButton getInstance() {
        if (ownButton==null) ownButton = new FlashlightButton();
        return ownButton;
    }


    public boolean getFlashlightEnabled() {
        return Settings.System.getInt(mContext.getContentResolver(), Settings.System.TORCH_STATE, 0) == 1;
    }

    @Override
    void initButton(int position) {
    }

}
