package com.google.android.settings.aware;

import android.content.Context;
import android.view.View;
import com.android.settings.aware.AwareFeatureProvider;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.overlay.FeatureFactory;
import com.android.settings.utils.AnnotationSpan;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.widget.FooterPreferenceMixinCompat;

abstract class AwareFooterPreferenceController extends BasePreferenceController implements LifecycleObserver, OnStart {
    private Context mContext;
    private final AwareFeatureProvider mFeatureProvider = FeatureFactory.getFactory(this.mContext).getAwareFeatureProvider();
    private FooterPreferenceMixinCompat mFooterMixin;

    public abstract int getMetricsCategory();

    public abstract int getText();

    public AwareFooterPreferenceController(Context context, String str) {
        super(context, str);
        this.mContext = context;
    }

    public int getAvailabilityStatus() {
        return this.mFeatureProvider.isSupported(this.mContext) ? 0 : 3;
    }

    public void onStart() {
        if (isAvailable()) {
            this.mFooterMixin.createFooterPreference().setTitle(getFooterText());
        }
    }

    public void setFooterMixin(FooterPreferenceMixinCompat footerPreferenceMixinCompat) {
        this.mFooterMixin = footerPreferenceMixinCompat;
    }

    private CharSequence getFooterText() {
        AnnotationSpan.LinkInfo linkInfo = getLinkInfo();
        if (linkInfo == null) {
            return this.mContext.getText(getText());
        }
        return AnnotationSpan.linkify(this.mContext.getText(getText()), linkInfo);
    }

    public AnnotationSpan.LinkInfo getLinkInfo() {
        return new AnnotationSpan.LinkInfo("link", new C1564x9fbd900a(this));
    }

    public  void getLinkInfoAwareFooterPreferenceController(View view) {
        new SubSettingLauncher(this.mContext).setDestination(AwareSettings.class.getName()).setSourceMetricsCategory(getMetricsCategory()).launch();
    }
}
