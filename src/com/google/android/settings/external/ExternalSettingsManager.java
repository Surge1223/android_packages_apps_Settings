package com.google.android.settings.external;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.android.settings.overlay.FeatureFactory;
import com.android.settings.slices.SliceData;
import com.android.settingslib.core.instrumentation.SharedPreferencesLogger;
import com.google.android.settings.external.specialcase.SliceBasedSetting;

public class ExternalSettingsManager {
    public static String getNewSettingValueQueryParameter(Uri uri) {
        return uri.getQueryParameter("new_setting_value");
    }
    public static Cursor getAccessCursorForSpecialSetting(Context context, String str, String str2, SliceData sliceData) {
        char c;
        Cursor cursor;
        int i = -1;
        switch (str2.hashCode()) {
            case -1903108977:
                if (str2.equals("ambient_display_notification")) {
                    c = 3;
                    break;
                }
            case -1552376189:
                if (str2.equals("gesture_pick_up")) {
                    c = 10;
                    break;
                }
            case -1525260053:
                if (str2.equals("ambient_display_always_on")) {
                    c = 8;
                    break;
                }
            case -1314247385:
                if (str2.equals("mobile_data")) {
                    c = 18;
                    break;
                }
            case -1272444437:
                if (str2.equals("auto_rotate")) {
                    c = 4;
                    break;
                }
            case -1001942051:
                if (str2.equals("toggle_airplane")) {
                    c = 2;
                    break;
                }
            case -610139245:
                if (str2.equals("talkback")) {
                    c = 21;
                    break;
                }
            case -608350689:
                if (str2.equals("gesture_assist")) {
                    c = 0;
                    break;
                }
            case -382039141:
                if (str2.equals("night_display")) {
                    c = 20;
                    break;
                }
            case -315259171:
                if (str2.equals("enable_wifi_ap")) {
                    c = 14;
                    break;
                }
            case -147951540:
                if (str2.equals("notification_badging")) {
                    c = 9;
                    break;
                }
            case 108971:
                if (str2.equals("nfc")) {
                    c = 19;
                    break;
                }
            case 15719777:
                if (str2.equals("master_wifi_toggle")) {
                    c = 22;
                    break;
                }
            case 392597729:
                if (str2.equals("auto_brightness")) {
                    c = 1;
                    break;
                }
            case 536948395:
                if (str2.equals("magnify_gesture")) {
                    c = 16;
                    break;
                }
            case 556955191:
                if (str2.equals("color_inversion")) {
                    c = 12;
                    break;
                }
            case 999778018:
                if (str2.equals("gesture_swipe_down_fingerprint")) {
                    c = 11;
                    break;
                }
            case 1043529839:
                if (str2.equals("gesture_double_twist")) {
                    c = 7;
                    break;
                }
            case 1436281521:
                if (str2.equals("gesture_double_tap_power")) {
                    c = 6;
                    break;
                }
            case 1599575662:
                if (str2.equals("magnify_navbar")) {
                    c = 17;
                    break;
                }
            case 1619122624:
                if (str2.equals("data_saver")) {
                    c = 13;
                    break;
                }
            case 1649710144:
                if (str2.equals("gesture_double_tap_screen")) {
                    c = 5;
                    break;
                }
            case 1901043637:
                if (str2.equals("location")) {
                    c = 15;
                    break;
                }
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                cursor = new SliceBasedSetting().getAccessCursor(context, sliceData);
                break;
            case 12:
                cursor = InlineSettings.COLOR_INVERSION_SETTING.getAccessCursor(context, sliceData);
                break;
            case 13:
                cursor = InlineSettings.DATA_SAVER_SETTING.getAccessCursor(context, sliceData);
                break;
            case 14:
                cursor = InlineSettings.HOTSPOT_SETTING.getAccessCursor(context, sliceData);
                break;
            case 15:
                cursor = InlineSettings.LOCATION_SETTING.getAccessCursor(context, sliceData);
                break;
            case 16:
                cursor = InlineSettings.MAGNIFY_GESTURE_SETTING.getAccessCursor(context, sliceData);
                break;
            case 17:
                cursor = InlineSettings.MAGNIFY_NAVBAR_SETTING.getAccessCursor(context, sliceData);
                break;
            case 18:
                cursor = InlineSettings.MOBILE_DATA_SETTING.getAccessCursor(context, sliceData);
                break;
            case 19:
                cursor = InlineSettings.NFC_SETTING.getAccessCursor(context, sliceData);
                break;
            case 20:
                cursor = InlineSettings.NIGHTDISPLAY_SETTING.getAccessCursor(context, sliceData);
                break;
            case 21:
                cursor = InlineSettings.TALKBACK_SETTING.getAccessCursor(context, sliceData);
                break;
            case 22:
                cursor = InlineSettings.WIFI_SETTING.getAccessCursor(context, sliceData);
                break;
            default:
                cursor = null;
                break;
        }
        if (cursor == null || !cursor.moveToFirst()) {
            throw new IllegalArgumentException("Invalid access special case key: " + str2);
        }
        int columnIndex = cursor.getColumnIndex("existing_value");
        if (columnIndex >= 0) {
            i = cursor.getInt(columnIndex);
        }
        logAccessSetting(context, str, str2, i);
        return cursor;
    }

    public static Cursor getUpdateCursorForSpecialSetting(Context context, String str, String str2, String str3, SliceData sliceData) {
        char c;
        Cursor cursor;
        int i = -1;
        switch (str2.hashCode()) {
            case -1903108977:
                if (str2.equals("ambient_display_notification")) {
                    c = 3;
                    break;
                }
            case -1552376189:
                if (str2.equals("gesture_pick_up")) {
                    c = 10;
                    break;
                }
            case -1525260053:
                if (str2.equals("ambient_display_always_on")) {
                    c = 8;
                    break;
                }
            case -1314247385:
                if (str2.equals("mobile_data")) {
                    c = 18;
                    break;
                }
            case -1272444437:
                if (str2.equals("auto_rotate")) {
                    c = 4;
                    break;
                }
            case -1001942051:
                if (str2.equals("toggle_airplane")) {
                    c = 2;
                    break;
                }
            case -610139245:
                if (str2.equals("talkback")) {
                    c = 21;
                    break;
                }
            case -608350689:
                if (str2.equals("gesture_assist")) {
                    c = 0;
                    break;
                }
            case -382039141:
                if (str2.equals("night_display")) {
                    c = 20;
                    break;
                }
            case -315259171:
                if (str2.equals("enable_wifi_ap")) {
                    c = 14;
                    break;
                }
            case -147951540:
                if (str2.equals("notification_badging")) {
                    c = 9;
                    break;
                }
            case 108971:
                if (str2.equals("nfc")) {
                    c = 19;
                    break;
                }
            case 15719777:
                if (str2.equals("master_wifi_toggle")) {
                    c = 22;
                    break;
                }
            case 392597729:
                if (str2.equals("auto_brightness")) {
                    c = 1;
                    break;
                }
            case 536948395:
                if (str2.equals("magnify_gesture")) {
                    c = 16;
                    break;
                }
            case 556955191:
                if (str2.equals("color_inversion")) {
                    c = 12;
                    break;
                }
            case 999778018:
                if (str2.equals("gesture_swipe_down_fingerprint")) {
                    c = 11;
                    break;
                }
            case 1043529839:
                if (str2.equals("gesture_double_twist")) {
                    c = 7;
                    break;
                }
            case 1436281521:
                if (str2.equals("gesture_double_tap_power")) {
                    c = 6;
                    break;
                }
            case 1599575662:
                if (str2.equals("magnify_navbar")) {
                    c = 17;
                    break;
                }
            case 1619122624:
                if (str2.equals("data_saver")) {
                    c = 13;
                    break;
                }
            case 1649710144:
                if (str2.equals("gesture_double_tap_screen")) {
                    c = 5;
                    break;
                }
            case 1901043637:
                if (str2.equals("location")) {
                    c = 15;
                    break;
                }
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                cursor = new SliceBasedSetting().getUpdateCursor(context, sliceData, str3);
                break;
            case 12:
                cursor = InlineSettings.COLOR_INVERSION_SETTING.getUpdateCursor(context, sliceData, str3);
                break;
            case 13:
                cursor = InlineSettings.DATA_SAVER_SETTING.getUpdateCursor(context, sliceData, str3);
                break;
            case 14:
                cursor = InlineSettings.HOTSPOT_SETTING.getUpdateCursor(context, sliceData, str3);
                break;
            case 15:
                cursor = InlineSettings.LOCATION_SETTING.getUpdateCursor(context, sliceData, str3);
                break;
            case 16:
                cursor = InlineSettings.MAGNIFY_GESTURE_SETTING.getUpdateCursor(context, sliceData, str3);
                break;
            case 17:
                cursor = InlineSettings.MAGNIFY_NAVBAR_SETTING.getUpdateCursor(context, sliceData, str3);
                break;
            case 18:
                cursor = InlineSettings.MOBILE_DATA_SETTING.getUpdateCursor(context, sliceData, str3);
                break;
            case 19:
                cursor = InlineSettings.NFC_SETTING.getUpdateCursor(context, sliceData, str3);
                break;
            case 20:
                cursor = InlineSettings.NIGHTDISPLAY_SETTING.getUpdateCursor(context, sliceData, str3);
                break;
            case 21:
                cursor = InlineSettings.TALKBACK_SETTING.getUpdateCursor(context, sliceData, str3);
                break;
            case 22:
                cursor = InlineSettings.WIFI_SETTING.getUpdateCursor(context, sliceData, str3);
                break;
            default:
                cursor = null;
                break;
        }
        if (cursor == null || !cursor.moveToFirst()) {
            throw new IllegalArgumentException("Invalid update special case key: " + str2);
        }
        int columnIndex = cursor.getColumnIndex("newValue");
        if (columnIndex >= 0) {
            i = cursor.getInt(columnIndex);
        }
        logUpdateSetting(context, str, str2, i);
        return cursor;
    }

    private static void logAccessSetting(Context context, String str, String str2, int i) {
        FeatureFactory.getFactory(context).getMetricsFeatureProvider().action(0, 853, 0, SharedPreferencesLogger.buildPrefKey(str + "/access", str2), i);
    }

    private static void logUpdateSetting(Context context, String str, String str2, int i) {
        FeatureFactory.getFactory(context).getMetricsFeatureProvider().action(0, 853, 0, SharedPreferencesLogger.buildPrefKey(str, str2), i);
    }
}
