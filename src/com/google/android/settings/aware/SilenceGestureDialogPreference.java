package com.google.android.settings.aware;

import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import com.android.settings.R;

public class SilenceGestureDialogPreference extends AwareGestureDialogPreference {
    public int getDialogDisabledMessage() {
        return R.string.gesture_aware_disabled_info_dialog_content;
    }

    public int getGestureDialogMessage() {
        return R.string.gesture_aware_off_dialog_content;
    }

    public int getGestureDialogTitle() {
        return R.string.gesture_aware_off_dialog_title;
    }

    public int getSourceMetricsCategory() {
        return 459;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        super.onClick(dialogInterface, i);
    }

    public SilenceGestureDialogPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public String getDestination() {
        return SilenceGestureSettings.class.getName();
    }
}
