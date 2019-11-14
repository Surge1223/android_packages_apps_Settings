package com.google.android.settings.aware;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import com.android.settings.aware.AwareFeatureProvider;
import com.android.settings.overlay.FeatureFactory;

public class AwareHelper {
    private final String SHARE_PERFS = "aware_settings";
    public final Context mContext;
    private final AwareFeatureProvider mFeatureProvider;
    private final SettingsObserver mSettingsObserver;

    public interface Callback {
        void onChange(Uri uri);
    }

    private final class SettingsObserver extends ContentObserver {
        private final Uri AIRPLANE_MODE = Settings.Global.getUriFor("airplane_mode_on");
        private final Uri AWARE_ALLOWED = Settings.Global.getUriFor("aware_allowed");
        private final Uri AWARE_ENABLED = Settings.Secure.getUriFor("aware_enabled");
        private Callback mCallback;

        public SettingsObserver(Handler handler) {
            super(handler);
        }

        public void setCallback(Callback callback) {
            this.mCallback = callback;
        }

        public void observe() {
            ContentResolver contentResolver = AwareHelper.this.mContext.getContentResolver();
            contentResolver.registerContentObserver(this.AWARE_ENABLED, false, this);
            contentResolver.registerContentObserver(this.AWARE_ALLOWED, false, this);
            contentResolver.registerContentObserver(this.AIRPLANE_MODE, false, this);
        }

        public void onChange(boolean z, Uri uri) {
            Callback callback = this.mCallback;
            if (callback != null) {
                callback.onChange(uri);
            }
        }
    }

    public AwareHelper(Context context) {
        this.mContext = context;
        this.mSettingsObserver = new SettingsObserver(new Handler(Looper.getMainLooper()));
        this.mFeatureProvider = FeatureFactory.getFactory(this.mContext).getAwareFeatureProvider();
    }

    public boolean isGestureConfigurable() {
        return this.mFeatureProvider.isSupported(this.mContext) && this.mFeatureProvider.isEnabled(this.mContext) && !isAirplaneModeOn();
    }

    public boolean isAirplaneModeOn() {
        return Settings.Global.getInt(this.mContext.getContentResolver(), "airplane_mode_on", 0) == 1;
    }

    public boolean isSupported() {
        return this.mFeatureProvider.isSupported(this.mContext);
    }

    public boolean isEnabled() {
        return this.mFeatureProvider.isEnabled(this.mContext);
    }

    public void register(Callback callback) {
        this.mSettingsObserver.observe();
        this.mSettingsObserver.setCallback(callback);
    }

    public void unregister() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mSettingsObserver);
    }

    public void writeFeatureEnabled(String str, boolean z) {
        this.mContext.getSharedPreferences("aware_settings", 0).edit().putBoolean(str, z).apply();
    }

    public boolean readFeatureEnabled(String str) {
        return this.mContext.getSharedPreferences("aware_settings", 0).getBoolean(str, true);
    }
}
