package com.google.android.settings.gestures.assist;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.gestures.AssistGestureFeatureProvider;
import com.android.settings.gestures.GesturePreferenceController;
import com.android.settings.overlay.FeatureFactory;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnResume;

public class AssistGesturePreferenceController extends GesturePreferenceController implements LifecycleObserver, OnResume {
    @VisibleForTesting
    static final int OFF = 0;
    @VisibleForTesting
    static final int ON = 1;
    private static final String PREF_KEY_VIDEO = "gesture_assist_video";
    private static final String SECURE_KEY_ASSIST = "assist_gesture_enabled";
    private final AssistGestureFeatureProvider mFeatureProvider;
    private Preference mPreference;
    private PreferenceScreen mScreen;

    public AssistGesturePreferenceController(Context context, String str) {
        super(context, str);
        this.mFeatureProvider = FeatureFactory.getFactory(context).getAssistGestureFeatureProvider();
    }

    public String getVideoPrefKey() {
        return PREF_KEY_VIDEO;
    }

    public void onResume() {
        updatePreference();
    }

    public void displayPreference(PreferenceScreen preferenceScreen) {
        this.mScreen = preferenceScreen;
        this.mPreference = preferenceScreen.findPreference(getPreferenceKey());
        super.displayPreference(preferenceScreen);
    }

    public int getAvailabilityStatus() {
        return this.mFeatureProvider.isSupported(this.mContext) ? 0 : 3;
    }

    public boolean isSliceable() {
        return TextUtils.equals(getPreferenceKey(), "gesture_assist");
    }

    public boolean isChecked() {
        return Settings.Secure.getInt(this.mContext.getContentResolver(), SECURE_KEY_ASSIST, 1) != 0;
    }

    public boolean setChecked(boolean z) {
        return Settings.Secure.putInt(this.mContext.getContentResolver(), SECURE_KEY_ASSIST, z ? 1 : 0);
    }

    private void updatePreference() {
        if (this.mPreference != null) {
            PreferenceScreen preferenceScreen = this.mScreen;
            if (preferenceScreen != null) {
                setVisible(preferenceScreen, getPreferenceKey(), isAvailable());
            }
        }
    }
}
