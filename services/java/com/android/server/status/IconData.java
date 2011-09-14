/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.server.status;

import android.provider.Settings;
import android.util.Slog;

public class IconData {
    /**
     * Indicates ths item represents a piece of text.
     */
    public static final int TEXT = 1;
    
    /**
     * Indicates ths item represents an icon.
     */
    public static final int ICON = 2;

    /**
      * Indicates ths item represents an integer displayed on top of an icon.
      */
    public static final int ICON_NUMBER = 3;

    /**
     * Default colors to use for any text appearing on each type of icon.
     */
    private static final int DEFAULT_TEXT_COLOR = 0xffffffff;
    private static final int DEFAULT_ICON_COLOR = 0xffffffff;
    private static final int DEFAULT_ICON_NUMBER_COLOR = 0xffffffff;

    /**
      * The type of this item. One of TEXT, ICON, or LEVEL_ICON.
      */
    public int type;

    /**
     * The slot that this icon will be in if it is not a notification
     */
    public String slot;

    /**
     * The package containting the icon to draw for this item. Valid if this is
     * an ICON type.
     */
    public String iconPackage;
    
    /**
     * The icon to draw for this item. Valid if this is an ICON type.
     */
    public int iconId;
    
    /**
     * The level associated with the icon. Valid if this is a LEVEL_ICON type.
     */
    public int iconLevel;
    
    /**
     * The "count" number.
     */
    public int number;

    /**
     * The text associated with the icon. Valid if this is a TEXT type.
     */
    public CharSequence text;

    /**
     * The default color of any text associated with this icon.
     */
    public int textColor;

    /**
     * The system setting that holds the text color for this icon.
     */
    public String colorSetting;

    /**
     * The system setting that determines whether the icon is visible or not.
     * Currently this only applies to the TEXT type.
     */
    public String visibleSetting;
    public boolean defVisibility;

    private IconData() {
    }

    public static IconData makeIcon(String slot,
            String iconPackage, int iconId, int iconLevel, int number) {
        IconData data = new IconData();
        data.type = ICON;
        data.slot = slot;
        data.iconPackage = iconPackage;
        data.iconId = iconId;
        data.iconLevel = iconLevel;
        data.number = number;
        data.textColor = DEFAULT_ICON_COLOR;
        data.colorSetting = Settings.System.NOTIF_COUNT_COLOR;
        return data;
    }
    
    public static IconData makeText(String slot, CharSequence text, String colorSetting,
            String visibleSetting, boolean defVisibility) {
        IconData data = new IconData();
        data.type = TEXT;
        data.slot = slot;
        data.text = text;
        data.textColor = DEFAULT_TEXT_COLOR;
        data.colorSetting = colorSetting;
        data.visibleSetting = visibleSetting;
        data.defVisibility = defVisibility;
        return data;
    }

    public static IconData makeIconNumber(String slot,
            String iconPackage, int iconId, int iconLevel, int number, String colorSetting) {
        IconData data = new IconData();
        data.type = ICON_NUMBER;
        data.slot = slot;
        data.iconPackage = iconPackage;
        data.iconId = iconId;
        data.iconLevel = iconLevel;
        data.number = number;
        data.textColor = DEFAULT_ICON_NUMBER_COLOR;
        data.colorSetting = colorSetting;
        return data;
    }

    public void copyFrom(IconData that) {
        this.type = that.type;
        this.slot = that.slot;
        this.iconPackage = that.iconPackage;
        this.iconId = that.iconId;
        this.iconLevel = that.iconLevel;
        this.number = that.number;
        this.text = that.text; // should we clone this?
        this.textColor = that.textColor;
        this.colorSetting = that.colorSetting;
        this.visibleSetting = that.visibleSetting;
        this.defVisibility = that.defVisibility;
    }

    public IconData clone() {
        IconData that = new IconData();
        that.copyFrom(this);
        return that;
    }

    public String toString() {
        if (this.type == TEXT) {
            return "IconData(slot=" + (this.slot != null ? "'" + this.slot + "'" : "null")
                    + " text='" + this.text + "')"; 
        }
        else if (this.type == ICON) {
            return "IconData(slot=" + (this.slot != null ? "'" + this.slot + "'" : "null")
                    + " package=" + this.iconPackage
                    + " iconId=" + Integer.toHexString(this.iconId)
                    + " iconLevel=" + this.iconLevel + ")"; 
        }

        else if (this.type == ICON_NUMBER) {
            return "IconData(slot=" + (this.slot != null ? "'" + this.slot + "'" : "null")
                    + " package=" + this.iconPackage
                    + " iconId=" + Integer.toHexString(this.iconId)
                    + " iconLevel=" + this.iconLevel
                    + " number='" + this.number + "')"; 
        }
        else {
            return "IconData(type=" + type + ")";
        }
    }
}
