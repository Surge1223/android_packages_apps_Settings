package com.google.android.settings.widget;

import android.content.Context;
import android.os.SystemProperties;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;

import com.google.android.setupdesign.view.IllustrationVideoView;

public class MarlinColorFingerprintLocationAnimationVideoView extends IllustrationVideoView {
    public MarlinColorFingerprintLocationAnimationVideoView(Context context, AttributeSet attributeSet) {
        super(getDeviceColorTheme(context), attributeSet);
    }

    private static Context getDeviceColorTheme(Context context) {
        int i;
        String str = SystemProperties.get("ro.boot.hardware.color");
        if ("BLU00".equals(str)) {
            i = 2131951908;
        } else if ("SLV00".equals(str)) {
            i = 2131951910;
        } else if ("GRA00".equals(str)) {
                i = 2131951823;
            }
            else {
                i = 2131951821;
            }
            return (Context)new ContextThemeWrapper(context, i);
        }
    }
