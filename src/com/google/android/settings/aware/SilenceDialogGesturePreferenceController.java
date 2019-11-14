package com.google.android.settings.aware;

import android.content.Context;
import android.provider.Settings;
import com.android.settings.R;

public class SilenceDialogGesturePreferenceController extends AwareGesturesCategoryPreferenceController {
    public SilenceDialogGesturePreferenceController(Context context, String str) {
        super(context, str);
    }

    private boolean isSilenceGestureEnabled() {
        if (!this.mFeatureProvider.isEnabled(this.mContext) || Settings.Secure.getInt(this.mContext.getContentResolver(), "silence_gesture", 1) != 1) {
            return false;
        }
        return true;
    }

    public CharSequence getSummary() {
        return this.mContext.getText(isSilenceGestureEnabled() ? R.string.gesture_silence_on_summary : R.string.gesture_setting_off);
    }
}
