package com.android.server.status.widget;

import com.android.internal.R;
import com.android.server.status.widget.PowerButton;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.widget.Toast;
import android.provider.Settings;

public class LockScreenButton extends PowerButton {

    Context mContext;

    static Boolean lockScreen = null;

    public static final String LOCK_SCREEN = "lockScreen";
    static LockScreenButton ownButton = null;
    KeyguardLock lock;

    private KeyguardLock getLock(Context context) {
        if (lock == null) {
            KeyguardManager keyguardManager = (KeyguardManager)context.
                    getSystemService(Activity.KEYGUARD_SERVICE);
            lock = keyguardManager.newKeyguardLock(Context.KEYGUARD_SERVICE);
        }
        return lock;
    }

    public void updateState(Context context) {
        mContext = context;

        getState(context);
        if (lockScreen == null) {
            currentIcon = R.drawable.stat_lock_screen_off;
            currentState = PowerButton.STATE_INTERMEDIATE;
        } else if (lockScreen) {
            currentIcon = R.drawable.stat_lock_screen_on;
            currentState = PowerButton.STATE_ENABLED;
        } else {
            currentIcon = R.drawable.stat_lock_screen_off;
            currentState = PowerButton.STATE_DISABLED;
        }
    }

    public void callSettings(Context context) {
        Intent intent = new Intent("android.settings.SECURITY_SETTINGS");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * Toggles the state of GPS.
     *
     * @param context
     */
    public void toggleState(Context context) {
        getState(context);
        if(lockScreen == null) {
            Toast msg = Toast.makeText(context, "Not yet initialized", Toast.LENGTH_LONG);
            msg.setGravity(Gravity.CENTER, msg.getXOffset() / 2, msg.getYOffset() / 2);
            msg.show();
        } else {
            getLock(context);
            if (lockScreen && lock != null) {
                lock.disableKeyguard();
                lockScreen = false;
            } else if (lock != null) {
                lock.reenableKeyguard();
                lockScreen = true;
            }
        }
    }

    /**
     * Gets the state of GPS location.
     *
     * @param context
     * @return true if enabled.
     */
    private static boolean getState(Context context) {
        if (lockScreen == null) {
            lockScreen = true;
        }
        return lockScreen;
    }


    public static LockScreenButton getInstance() {
        if (ownButton==null) ownButton = new LockScreenButton();

        return ownButton;
    }

    @Override
    void initButton(int position) {
    }
}

