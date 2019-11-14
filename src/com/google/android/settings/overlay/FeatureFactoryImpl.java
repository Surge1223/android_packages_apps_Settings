package com.google.android.settings.overlay;

import android.app.AppGlobals;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.util.Log;
import com.android.settings.accounts.AccountFeatureProvider;
import com.android.settings.applications.ApplicationFeatureProvider;
import com.android.settings.aware.AwareFeatureProvider;
import com.android.settings.dashboard.suggestions.SuggestionFeatureProvider;
import com.android.settings.fuelgauge.PowerUsageFeatureProvider;
import com.android.settings.gestures.AssistGestureFeatureProvider;
import com.android.settings.overlay.DockUpdaterFeatureProvider;
import com.android.settings.overlay.SupportFeatureProvider;
import com.android.settings.overlay.SurveyFeatureProvider;
import com.android.settings.search.SearchFeatureProvider;
import com.google.android.settings.accounts.AccountFeatureProviderGoogleImpl;
import com.google.android.settings.applications.ApplicationFeatureProviderGoogleImpl;
import com.google.android.settings.aware.AwareFeatureProviderGoogleImpl;
import com.google.android.settings.connecteddevice.dock.DockUpdaterFeatureProviderGoogleImpl;
import com.google.android.settings.dashboard.suggestions.SuggestionFeatureProviderGoogleImpl;
import com.google.android.settings.experiments.GServicesProxy;
import com.google.android.settings.fuelgauge.PowerUsageFeatureProviderGoogleImpl;
import com.google.android.settings.gestures.assist.AssistGestureFeatureProviderGoogleImpl;
import com.google.android.settings.search.SearchFeatureProviderGoogleImpl;
//import com.google.android.settings.support.SupportFeatureProviderImpl;
import com.google.android.settings.survey.SurveyFeatureProviderImpl;

public final class FeatureFactoryImpl extends com.android.settings.overlay.FeatureFactoryImpl {
    private AccountFeatureProvider mAccountFeatureProvider;
    private ApplicationFeatureProvider mApplicationFeatureProvider;
    private AssistGestureFeatureProvider mAssistGestureFeatureProvider;
    private AwareFeatureProvider mAwareFeatureProvider;
    private DockUpdaterFeatureProvider mDockUpdaterFeatureProvider;
    private PowerUsageFeatureProvider mPowerUsageProvider;
    private SearchFeatureProvider mSearchFeatureProvider;
    private SuggestionFeatureProvider mSuggestionFeatureProvider;
    private SupportFeatureProvider mSupportProvider;
    private SurveyFeatureProvider mSurveyFeatureProvider;

    public ApplicationFeatureProvider getApplicationFeatureProvider(Context context) {
        if (this.mApplicationFeatureProvider == null) {
            Context applicationContext = context.getApplicationContext();
            this.mApplicationFeatureProvider = new ApplicationFeatureProviderGoogleImpl(applicationContext, applicationContext.getPackageManager(), AppGlobals.getPackageManager(), (DevicePolicyManager) applicationContext.getSystemService("device_policy"));
        }
        return this.mApplicationFeatureProvider;
    }

    public SupportFeatureProvider getSupportFeatureProvider(Context context) {
        if (this.mSupportProvider == null) {
            this.mSupportProvider = new SupportFeatureProviderImpl(context.getApplicationContext());
        }
        return this.mSupportProvider;
    }

    public PowerUsageFeatureProvider getPowerUsageFeatureProvider(Context context) {
        if (this.mPowerUsageProvider == null) {
            this.mPowerUsageProvider = new PowerUsageFeatureProviderGoogleImpl(context.getApplicationContext());
        }
        return this.mPowerUsageProvider;
    }

    public DockUpdaterFeatureProvider getDockUpdaterFeatureProvider() {
        if (this.mDockUpdaterFeatureProvider == null) {
            this.mDockUpdaterFeatureProvider = new DockUpdaterFeatureProviderGoogleImpl();
        }
        return this.mDockUpdaterFeatureProvider;
    }

    public SearchFeatureProvider getSearchFeatureProvider() {
        if (this.mSearchFeatureProvider == null) {
            this.mSearchFeatureProvider = new SearchFeatureProviderGoogleImpl();
        }
        return this.mSearchFeatureProvider;
    }

    public SurveyFeatureProvider getSurveyFeatureProvider(Context context) {
        boolean z = false;
        try {
            z = GServicesProxy.getBoolean(context.getContentResolver(), "settingsgoogle:surveys_enabled", false);
        } catch (SecurityException e) {
            Log.w("FeatureFactoryImpl", "Error reading survey feature enabled state", e);
        }
        if (!z) {
            return null;
        }
        if (this.mSurveyFeatureProvider == null) {
            this.mSurveyFeatureProvider = new SurveyFeatureProviderImpl(context);
        }
        return this.mSurveyFeatureProvider;
    }

    public SuggestionFeatureProvider getSuggestionFeatureProvider(Context context) {
        if (this.mSuggestionFeatureProvider == null) {
            this.mSuggestionFeatureProvider = new SuggestionFeatureProviderGoogleImpl(context.getApplicationContext());
        }
        return this.mSuggestionFeatureProvider;
    }

    public AssistGestureFeatureProvider getAssistGestureFeatureProvider() {
        if (this.mAssistGestureFeatureProvider == null) {
            this.mAssistGestureFeatureProvider = new AssistGestureFeatureProviderGoogleImpl();
        }
        return this.mAssistGestureFeatureProvider;
    }

    public AccountFeatureProvider getAccountFeatureProvider() {
        if (this.mAccountFeatureProvider == null) {
            this.mAccountFeatureProvider = new AccountFeatureProviderGoogleImpl();
        }
        return this.mAccountFeatureProvider;
    }

    public AwareFeatureProvider getAwareFeatureProvider() {
        if (this.mAwareFeatureProvider == null) {
            this.mAwareFeatureProvider = new AwareFeatureProviderGoogleImpl();
        }
        return this.mAwareFeatureProvider;
    }
}
