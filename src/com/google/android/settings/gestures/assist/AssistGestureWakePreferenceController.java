package com.google.android.settings.gestures.assist;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreference;

import com.android.settings.gestures.AssistGestureFeatureProvider;
import com.android.settings.gestures.GesturePreferenceController;
import com.android.settings.overlay.FeatureFactory;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

public class AssistGestureWakePreferenceController extends GesturePreferenceController implements OnPause, OnResume, LifecycleObserver {
    private static final String PREF_KEY_VIDEO = "gesture_assist_video";
    private final AssistGestureFeatureProvider mFeatureProvider;
    private final SettingObserver mSettingObserver = new SettingObserver();
    public Handler mHandler = new Handler( Looper.getMainLooper() );
    private SwitchPreference mPreference;
    private PreferenceScreen mScreen;

    public AssistGestureWakePreferenceController(Context context, String str) {
        super( context, str );
        mFeatureProvider = FeatureFactory.getFactory(context).getAssistGestureFeatureProvider();
    }

    public String getVideoPrefKey() {
        return PREF_KEY_VIDEO;
    }

    public int getAvailabilityStatus() {
        return mFeatureProvider.isSensorAvailable( mContext ) ? 0 : 3;
    }

    public boolean isSliceable() {
        return TextUtils.equals( getPreferenceKey(), "gesture_assist_wake" );
    }

    public void displayPreference(PreferenceScreen preferenceScreen) {
        mScreen = preferenceScreen;
        mPreference = preferenceScreen.findPreference( getPreferenceKey() );
        if (!mFeatureProvider.isSupported( mContext )) {
            mScreen.removePreference( mPreference );
        } else {
            super.displayPreference( preferenceScreen );
        }
    }

    public boolean setChecked(boolean z) {
        return Settings.Secure.putInt(
                mContext.getContentResolver(), "assist_gesture_wake_enabled", z ? 1 : 0 );
    }

    public boolean isChecked() {
        return Settings.Secure.getInt(
                mContext.getContentResolver(), "assist_gesture_wake_enabled", 1 ) != 0;
    }

    public void onPause() {
        mSettingObserver.unregister();
    }

    public void onResume() {
        mSettingObserver.register();
        updatePreference();
    }

    public boolean canHandleClicks() {
        return Settings.Secure.getInt(
                mContext.getContentResolver(), "assist_gesture_enabled", 1 ) != 0;
    }

    public void updatePreference() {
        if (mPreference != null) {
            if (mFeatureProvider.isSupported( mContext )) {
                if (mScreen.findPreference( getPreferenceKey() ) == null) {
                    mScreen.addPreference( mPreference );
                }
                mPreference.setEnabled( canHandleClicks() );
                return;
            }
            mScreen.removePreference( mPreference );
        }
    }

    class SettingObserver extends ContentObserver {
        private final Uri ASSIST_GESTURE_ENABLED_URI = Settings.Secure.getUriFor( "assist_gesture_enabled" );

        public SettingObserver() {
            super( AssistGestureWakePreferenceController.this.mHandler );
        }

        public void register() {
            AssistGestureWakePreferenceController.this.mContext.getContentResolver().registerContentObserver( ASSIST_GESTURE_ENABLED_URI, false, this );
        }

        public void unregister() {
            AssistGestureWakePreferenceController.this.mContext.getContentResolver().unregisterContentObserver( this );
        }

        public void onChange(boolean z) {
            AssistGestureWakePreferenceController.this.updatePreference();
        }
    }
}
