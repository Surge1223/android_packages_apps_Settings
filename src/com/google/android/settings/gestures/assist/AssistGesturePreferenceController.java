package com.google.android.settings.gestures.assist;

import android.content.ContentResolver;
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

public class AssistGesturePreferenceController extends GesturePreferenceController implements LifecycleObserver, OnResume
{
    @VisibleForTesting
    static final int OFF = 0;
    @VisibleForTesting
    static final int ON = 1;
    private static final String PREF_KEY_VIDEO = "gesture_assist_video";
    private static final String SECURE_KEY_ASSIST = "assist_gesture_enabled";
    private final AssistGestureFeatureProvider mFeatureProvider;
    private Preference mPreference;
    private PreferenceScreen mScreen;

    public AssistGesturePreferenceController(final Context context, final String s) {
        super(context, s);
        this.mFeatureProvider = FeatureFactory.getFactory(context).getAssistGestureFeatureProvider();
    }

    private void updatePreference() {
        if (this.mPreference != null) {
            final PreferenceScreen mScreen = this.mScreen;
            if (mScreen != null) {
                this.setVisible(mScreen, this.getPreferenceKey(), this.isAvailable());
            }
        }
    }

    @Override
    public void displayPreference(final PreferenceScreen mScreen) {
        this.mScreen = mScreen;
        this.mPreference = mScreen.findPreference(this.getPreferenceKey());
        super.displayPreference(mScreen);
    }

    @Override
    public int getAvailabilityStatus() {
        int n;
        if (this.mFeatureProvider.isSupported(super.mContext)) {
            n = 0;
        }
        else {
            n = 3;
        }
        return n;
    }

    @Override
    protected String getVideoPrefKey() {
        return "gesture_assist_video";
    }

    @Override
    public boolean isChecked() {
        final ContentResolver contentResolver = super.mContext.getContentResolver();
        boolean b = true;
        if (Settings.Secure.getInt(contentResolver, "assist_gesture_enabled", 1) == 0) {
            b = false;
        }
        return b;
    }

    @Override
    public boolean isSliceable() {
        return TextUtils.equals((CharSequence)this.getPreferenceKey(), (CharSequence)"gesture_assist");
    }

    @Override
    public void onResume() {
        this.updatePreference();
    }

    @Override
    public boolean setChecked(final boolean b) {
        return Settings.Secure.putInt(super.mContext.getContentResolver(), "assist_gesture_enabled", (int)(b ? 1 : 0));
    }
}
