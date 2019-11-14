package com.google.android.settings.aware;

import android.app.Activity;
import android.content.Context;
import android.hardware.display.AmbientDisplayConfiguration;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import com.android.settings.core.SubSettingLauncher;

public class WakeScreenSuggestionActivity extends Activity {
    public static boolean isSuggestionComplete(Context context) {
        AwareHelper awareHelper = new AwareHelper(context);
        boolean wakeLockScreenGestureEnabled = new AmbientDisplayConfiguration(context).wakeLockScreenGestureEnabled(UserHandle.myUserId());
        boolean z = Settings.Global.getInt(context.getContentResolver(), "low_power", 0) == 1;
        if (wakeLockScreenGestureEnabled || z || !awareHelper.isGestureConfigurable()) {
            return true;
        }
        return false;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        new SubSettingLauncher(this).setDestination(WakeScreenGestureSettings.class.getName()).setSourceMetricsCategory(0).launch();
        finish();
    }
}
