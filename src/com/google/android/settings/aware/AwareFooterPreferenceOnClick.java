package com.google.android.settings.aware;

import android.view.View;

public class AwareFooterPreferenceOnClick implements View.OnClickListener {
    private AwareFooterPreferenceController awarefooterOnClick;

    public  AwareFooterPreferenceOnClick(AwareFooterPreferenceController awareFooterPreferenceController) {
        this.awarefooterOnClick = awareFooterPreferenceController;
    }

    public final void onClick(View view) {
        this.awarefooterOnClick.getLinkInfoAwareFooterPreferenceController(view);
    }
}
