package com.google.android.settings.aware;

import android.content.Context;
import com.android.settings.R;
import com.android.settingslib.widget.FooterPreferenceMixinCompat;

public class WakeScreenGestureFooterPreferenceController extends AwareFooterPreferenceController {
    public int getMetricsCategory() {
        return 1570;
    }

    public int getText() {
        return R.string.ambient_display_wake_screen_footer;
    }

    public int getAvailabilityStatus() {
        return super.getAvailabilityStatus();
    }

    public  void onStart() {
        super.onStart();
    }

    public void setFooterMixin(FooterPreferenceMixinCompat footerPreferenceMixinCompat) {
        super.setFooterMixin(footerPreferenceMixinCompat);
    }

    public WakeScreenGestureFooterPreferenceController(Context context) {
        super(context, "wake_screen_gesture_footer");
    }
}
