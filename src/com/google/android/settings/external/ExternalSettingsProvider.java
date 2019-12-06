package com.google.android.settings.external;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Binder;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.overlay.FeatureFactory;
import com.android.settings.slices.SliceData;
import com.android.settings.slices.SlicesDatabaseAccessor;
import com.android.settingslib.core.instrumentation.SharedPreferencesLogger;

// import com.google.android.settings.support.PsdBundle;

public class ExternalSettingsProvider extends ContentProvider
{
    private final int CODE_SETTINGS_MANAGER;
    private final int CODE_SETTINGS_SIGNALS;
    private final String TAG;
    private SlicesDatabaseAccessor mDatabaseAccessor;
    private UriMatcher mMatcher;

    public ExternalSettingsProvider() {
        this.TAG = "ExternalSettingProvider";
        this.CODE_SETTINGS_MANAGER = 1;
        this.CODE_SETTINGS_SIGNALS = 2;
    }

    private Cursor collectDeviceSignals(String buildPrefKey, final Uri uri) {
        if (!this.isSignalsApiEnabled()) {
            Log.i("ExternalSettingProvider", "Signals API disabled by gservices flag");
            return null;
        }
        final MatrixCursor matrixCursor = new MatrixCursor(ExternalSettingsContract.DEVICE_SIGNALS_COLUMNS);
//        final PsdBundle psdBundle = this.getPsdBundle(uri);
//        final String[] keys = psdBundle.getKeys();
//        final String[] values = psdBundle.getValues();
//        for (int i = 0; i < keys.length; ++i) {
//            matrixCursor.newRow().add("signal_key", (Object)keys[i]).add("signal_value", (Object)values[i]);
//        }
        Context context = this.getContext();
        buildPrefKey = SharedPreferencesLogger.buildPrefKey(buildPrefKey, "/signal");
        FeatureFactory.getFactory(context).getMetricsFeatureProvider().action(0, 853, 0, buildPrefKey, 0);
        return (Cursor)matrixCursor;
    }

    private Cursor querySettings(final Context context, final String s, final Uri uri) {
        final boolean settingsAccessApiEnabled = this.isSettingsAccessApiEnabled();
        final SliceData sliceData = null;
        if (!settingsAccessApiEnabled) {
            Log.i("ExternalSettingProvider", "Settings API disabled by gservices flag");
            return null;
        }
        final String lastPathSegment = uri.getLastPathSegment();
        final String newSettingValueQueryParameter = ExternalSettingsManager.getNewSettingValueQueryParameter(uri);
        SliceData sliceDataFromKey;
        try {
            sliceDataFromKey = this.mDatabaseAccessor.getSliceDataFromKey(lastPathSegment);
        }
        catch (IllegalStateException ex) {
            Log.w("ExternalSettingProvider", "Can't find slice key, defaulting to null");
            sliceDataFromKey = sliceData;
        }
        if (TextUtils.isEmpty((CharSequence)newSettingValueQueryParameter)) {
            return ExternalSettingsManager.getAccessCursorForSpecialSetting(context, s, lastPathSegment, sliceDataFromKey);
        }
        return ExternalSettingsManager.getUpdateCursorForSpecialSetting(context, s, lastPathSegment, newSettingValueQueryParameter, sliceDataFromKey);
    }

    public final void attachInfo(final Context context, final ProviderInfo providerInfo) {
        (this.mMatcher = new UriMatcher(-1)).addURI("com.google.android.settings.external", "settings_manager/*", 1);
        this.mMatcher.addURI("com.google.android.settings.external", "signals", 2);
        if (!providerInfo.exported) {
            throw new SecurityException("Provider must be exported");
        }
        if (providerInfo.grantUriPermissions) {
            super.attachInfo(context, providerInfo);
            return;
        }
        throw new SecurityException("Provider must grantUriPermissions");
    }

    public final int delete(final Uri uri, final String s, final String[] array) {
        throw new UnsupportedOperationException("Delete not supported");
    }

    @VisibleForTesting
//    PsdBundle getPsdBundle(final Uri uri) {
//        return PsdValuesLoader.makePsdBundle(this.getContext(), 2);
//    }

    public final String getType(final Uri uri) {
        throw new UnsupportedOperationException("MIME types not supported");
    }

    public final Uri insert(final Uri uri, final ContentValues contentValues) {
        throw new UnsupportedOperationException("Insert not supported");
    }

    @VisibleForTesting
    boolean isSettingsAccessApiEnabled() {
        boolean b = false;
        try {
            if (Settings.Global.getInt(this.getContext().getContentResolver(), "settings_use_external_provider_api", 0) != 0) {
                b = true;
            }
            return b;
        }
        catch (Exception ex) {
            Log.w("ExternalSettingProvider", "Error reading settings access api enabled flag", (Throwable)ex);
            return false;
        }
    }

    @VisibleForTesting
    boolean isSignalsApiEnabled() {
        boolean b = false;
        try {
            if (Settings.Global.getInt(this.getContext().getContentResolver(), "settings_use_psd_api", 0) != 0) {
                b = true;
            }
            return b;
        }
        catch (Exception ex) {
            Log.w("ExternalSettingProvider", "Error reading psd api enabled flag", (Throwable)ex);
            return false;
        }
    }

    public boolean onCreate() {
        this.mDatabaseAccessor = new SlicesDatabaseAccessor(this.getContext());
        return true;
    }

    public final Cursor query(final Uri obj, final String[] array, final String s, final String[] array2, final String s2) {
        final String verifyPermission = this.verifyPermission();
        final int match = this.mMatcher.match(obj);
        if (match == 1) {
            return this.querySettings(this.getContext(), verifyPermission, obj);
        }
        if (match == 2) {
            return this.collectDeviceSignals(verifyPermission, obj);
        }
        final StringBuilder sb = new StringBuilder();
        sb.append("Unknown Uri: ");
        sb.append(obj);
        throw new IllegalArgumentException(sb.toString());
    }

    public final int update(final Uri uri, final ContentValues contentValues, final String s, final String[] array) {
        throw new UnsupportedOperationException("Update not supported");
    }

    @VisibleForTesting
    String verifyPermission() throws SecurityException {
        return SignatureVerifier.verifyCallerIsWhitelisted(this.getContext(), Binder.getCallingUid());
    }
}

