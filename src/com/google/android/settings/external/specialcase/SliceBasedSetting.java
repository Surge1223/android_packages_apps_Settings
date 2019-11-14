package com.google.android.settings.external.specialcase;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.TogglePreferenceController;
import com.android.settings.slices.SliceBuilderUtils;
import com.android.settings.slices.SliceData;
import com.google.android.settings.external.ExternalSettingsContract;
import com.google.android.settings.external.Queryable;

public class SliceBasedSetting implements Queryable {
    static int mapAvailability(int i) {
        if (i == 0 || i == 1) {
            return 0;
        }
        if (i == 2 || i == 3) {
            return 2;
        }
        if (i != 4) {
            return i != 5 ? 2 : 1;
        }
        return 6;
    }

    public Cursor getAccessCursor(Context context, SliceData sliceData) {
        BasePreferenceController preferenceController = SliceBuilderUtils.getPreferenceController(context, sliceData);
        int isChecked = preferenceController instanceof TogglePreferenceController ? ((TogglePreferenceController) preferenceController).isChecked() : -1;
        MatrixCursor matrixCursor = new MatrixCursor(ExternalSettingsContract.EXTERNAL_SETTINGS_QUERY_COLUMNS);
        matrixCursor.newRow().add("existing_value", Integer.valueOf(isChecked)).add("availability", Integer.valueOf(mapAvailability(preferenceController.getAvailabilityStatus()))).add("intent", getIntentString(context, sliceData.getKey(), sliceData.getFragmentClassName(), sliceData.getScreenTitle().toString())).add("icon", Integer.valueOf(sliceData.getIconResource()));
        return matrixCursor;
    }


    public Cursor getUpdateCursor(Context context, SliceData sliceData, int i) {
        BasePreferenceController preferenceController = SliceBuilderUtils.getPreferenceController(context, sliceData);
        if (!(preferenceController instanceof TogglePreferenceController)) {
            return new MatrixCursor(ExternalSettingsContract.EXTERNAL_SETTINGS_UPDATE_COLUMNS);
        }
        TogglePreferenceController togglePreferenceController = (TogglePreferenceController) preferenceController;
        boolean isChecked = togglePreferenceController.isChecked();
        int availabilityStatus = preferenceController.getAvailabilityStatus();
        if (shouldChangeValue(availabilityStatus, isChecked ? 1 : 0, i)) {
            boolean z = true;
            if (i != 1) {
                z = false;
            }
        }
        i = isChecked;
        MatrixCursor matrixCursor = new MatrixCursor(ExternalSettingsContract.EXTERNAL_SETTINGS_UPDATE_COLUMNS);
        matrixCursor.newRow().add("newValue", Integer.valueOf(i)).add("existing_value", Integer.valueOf(isChecked)).add("availability", Integer.valueOf(availabilityStatus)).add("intent", SliceBuilderUtils.getContentIntent(context, sliceData).toUri(0)).add("icon", Integer.valueOf(sliceData.getIconResource()));
        return matrixCursor;
    }

    private static String getIntentString(Context context, String str, String str2, String str3) {
        return SliceBuilderUtils.buildSearchResultPageIntent(context, str2, str, str3, 1033).toUri(0);
    }
}
