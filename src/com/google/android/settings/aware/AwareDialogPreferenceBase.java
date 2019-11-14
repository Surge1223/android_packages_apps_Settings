package com.google.android.settings.aware;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import androidx.preference.PreferenceViewHolder;
import com.android.settings.R;
import com.android.settingslib.CustomDialogPreferenceCompat;
import com.google.android.settings.aware.AwareHelper;

public class AwareDialogPreferenceBase extends CustomDialogPreferenceCompat {
    protected AwareHelper mHelper;
    private View mInfoIcon;
    private View mSummary;
    private View mTitle;

    /* renamed from: com.google.android.settings.aware.AwareDialogPreferenceBase$1 */
    class C15651 implements AwareHelper.Callback {
        C15651() {
        }

        public void onChange(Uri uri) {
            AwareDialogPreferenceBase.this.updatePreference();
        }
    }

    /* access modifiers changed from: protected */
    public boolean isAvailable() {
        return false;
    }

    /* access modifiers changed from: protected */
    public void performEnabledClick() {
    }

    public AwareDialogPreferenceBase(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init();
    }

    public AwareDialogPreferenceBase(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public AwareDialogPreferenceBase(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public AwareDialogPreferenceBase(Context context) {
        super(context);
        init();
    }

    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        this.mTitle = preferenceViewHolder.findViewById(16908310);
        this.mSummary = preferenceViewHolder.findViewById(16908304);
        this.mInfoIcon = preferenceViewHolder.findViewById(R.id.info_button);
        updatePreference();
    }

    public void performClick() {
        if (isAvailable()) {
            performEnabledClick();
        } else if (!this.mHelper.isAirplaneModeOn()) {
            super.performClick();
        }
    }

    /* access modifiers changed from: protected */
    public void updatePreference() {
        View view = this.mTitle;
        if (view != null) {
            view.setEnabled(isAvailable());
        }
        View view2 = this.mSummary;
        if (view2 != null) {
            view2.setEnabled(isAvailable());
        }
        View view3 = this.mInfoIcon;
        if (view3 != null) {
            view3.setVisibility((isAvailable() || this.mHelper.isAirplaneModeOn()) ? 8 : 0);
        }
    }

    private void init() {
        setWidgetLayoutResource(R.layout.preference_widget_info);
        this.mHelper = new AwareHelper(getContext());
        this.mHelper.register(new C15651());
    }
}
