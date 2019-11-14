package com.google.android.settings.external.specialcase;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;

import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.TogglePreferenceController;
import com.android.settings.slices.SliceBuilderUtils;
import com.android.settings.slices.SliceData;
import com.google.android.settings.external.ExternalSettingsContract;

public class SliceBasedSetting implements SliceBasedSettingExtracted {
    private static String getIntentString(final Context context, final String s, final String s2, final String s3) {
        return SliceBuilderUtils.buildSearchResultPageIntent( context, s2, s, s3, 1033 ).toUri( 0 );
    }

    static int mapAvailability(final int n) {
        if (n == 0 || n == 1) {
            return 0;
        }
        if (n == 2 || n == 3) {
            return 2;
        }
        if (n == 4) {
            return 6;
        }
        if (n != 5) {
            return 2;
        }
        return 1;
    }

    @Override
    public Cursor getAccessCursor(final Context context, final SliceData sliceData) {
        final BasePreferenceController preferenceController = SliceBuilderUtils.getPreferenceController( context, sliceData );
        int checked;
        if (preferenceController instanceof TogglePreferenceController) {
            checked = (((TogglePreferenceController) preferenceController).isChecked() ? 1 : 0);
        } else {
            checked = -1;
        }
        MatrixCursor matrixCursor = new MatrixCursor( ExternalSettingsContract.EXTERNAL_SETTINGS_QUERY_COLUMNS );
        matrixCursor.newRow().add(
                  "existing_value", checked ).add
                ( "availability", mapAvailability( preferenceController.getAvailabilityStatus() ) ).add
                ( "intent", getIntentString( context, sliceData.getKey(), sliceData.getFragmentClassName(), sliceData.getScreenTitle().toString() ) ).add
                ( "icon", sliceData.getIconResource() );
        return matrixCursor;
    }

    @Override
    public Cursor getUpdateCursor(final Context context, final SliceData sliceData, int i) {
        final BasePreferenceController preferenceController = SliceBuilderUtils.getPreferenceController( context, sliceData );
        if (!(preferenceController instanceof TogglePreferenceController)) {
            return new MatrixCursor( ExternalSettingsContract.EXTERNAL_SETTINGS_UPDATE_COLUMNS );
        }
        final TogglePreferenceController togglePreferenceController = (TogglePreferenceController) preferenceController;
        final int checked = togglePreferenceController.isChecked() ? 1 : 0;
        final int availabilityStatus = preferenceController.getAvailabilityStatus();
        Label_0091:
        {
            if (this.shouldChangeValue( availabilityStatus, checked, i )) {
                boolean checked2 = true;
                if (i != 1) {
                    checked2 = false;
                }
                if (togglePreferenceController.setChecked( checked2 )) {
                    break Label_0091;
                }
            }
            i = checked;
        }
        final MatrixCursor matrixCursor = new MatrixCursor( ExternalSettingsContract.EXTERNAL_SETTINGS_UPDATE_COLUMNS );
        matrixCursor.newRow().add(
                  "newValue", i ).add
                ( "existing_value", checked ).add
                ( "availability", availabilityStatus ).add
                ( "intent", SliceBuilderUtils.getContentIntent( context, sliceData ).toUri( 0 ) ).add
                ( "icon", sliceData.getIconResource() );
        return matrixCursor;
    }
}

