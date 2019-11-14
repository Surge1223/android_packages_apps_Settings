package com.google.android.settings.aware;

import android.content.Context;
import com.android.settings.R;
import com.android.settingslib.widget.FooterPreferenceMixinCompat;

public class SilenceGestureFooterPreferenceController extends AwareFooterPreferenceController {
    public int getMetricsCategory() {
        return 1625;
    }

    public int getText() {
        return R.string.gesture_aware_footer;
    }

    public /* bridge */ /* synthetic */ int getAvailabilityStatus() {
        return super.getAvailabilityStatus();
    }

    public /* bridge */ /* synthetic */ void onStart() {
        super.onStart();
    }

    public /* bridge */ /* synthetic */ void setFooterMixin(FooterPreferenceMixinCompat footerPreferenceMixinCompat) {
        super.setFooterMixin(footerPreferenceMixinCompat);
    }

    public SilenceGestureFooterPreferenceController(Context context) {
        super(context, "silence_gesture_footer");
    }
}
