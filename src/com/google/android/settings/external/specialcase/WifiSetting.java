package com.google.android.settings.external.specialcase;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.wifi.WifiManager;
import com.android.settings.R;
import com.android.settings.slices.SliceData;
import com.android.settings.wifi.WifiSettings;
import com.google.android.settings.external.ExternalSettingsContract;
import com.google.android.settings.external.Queryable;

public class WifiSetting implements Queryable {
    private int getIconResource() {
        return R.drawable.ic_settings_wireless;
    }

    public Cursor getAccessCursor(Context context, SliceData sliceData) {
        boolean isWifiEnabled = ((WifiManager) context.getSystemService("wifi")).isWifiEnabled();
        String intentString = getIntentString(context, "master_wifi_toggle", WifiSettings.class, getScreenTitle(context));
        int iconResource = getIconResource();
        MatrixCursor matrixCursor = new MatrixCursor(ExternalSettingsContract.EXTERNAL_SETTINGS_QUERY_COLUMNS);
        matrixCursor.newRow().add("existing_value", Integer.valueOf(isWifiEnabled ? 1 : 0)).add("availability", 0).add("intent", intentString).add("icon", Integer.valueOf(iconResource));
        return matrixCursor;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x002c, code lost:
        if (r6.setWifiEnabled(r4) != false) goto L_0x0030;
     */
    public Cursor getUpdateCursor(Context context, SliceData sliceData, int i) {
        WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
        boolean isWifiEnabled = wifiManager.isWifiEnabled();
        String intentString = getIntentString(context, "master_wifi_toggle", WifiSettings.class, getScreenTitle(context));
        int iconResource = getIconResource();
        if (shouldChangeValue(0, isWifiEnabled ? 1 : 0, i)) {
            boolean z = true;
            if (i != 1) {
                z = false;
            }
        }
        i = isWifiEnabled;
        MatrixCursor matrixCursor = new MatrixCursor(ExternalSettingsContract.EXTERNAL_SETTINGS_UPDATE_COLUMNS);
        matrixCursor.newRow().add("newValue", Integer.valueOf(i)).add("existing_value", Integer.valueOf(isWifiEnabled)).add("availability", 0).add("intent", intentString).add("icon", Integer.valueOf(iconResource));
        return matrixCursor;
    }

    private String getScreenTitle(Context context) {
        return context.getString(R.string.wifi_settings);
    }
}
