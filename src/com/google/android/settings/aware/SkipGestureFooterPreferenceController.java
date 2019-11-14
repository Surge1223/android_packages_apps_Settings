package com.google.android.settings.aware;

import android.content.Context;
import com.android.settings.R;
import com.android.settingslib.widget.FooterPreferenceMixinCompat;

public class SkipGestureFooterPreferenceController extends AwareFooterPreferenceController {
    public int getMetricsCategory() {
        return 1624;
    }

    public int getText() {
        return R.string.gesture_aware_footer;
    }

    public int getAvailabilityStatus() {
        return super.getAvailabilityStatus();
    }

    public  void onStart() {
        super.onStart();
    }

    public  void setFooterMixin(FooterPreferenceMixinCompat footerPreferenceMixinCompat) {
        super.setFooterMixin(footerPreferenceMixinCompat);
    }

    public SkipGestureFooterPreferenceController(Context context) {
        super(context, "skip_gesture_footer");
    }
}
