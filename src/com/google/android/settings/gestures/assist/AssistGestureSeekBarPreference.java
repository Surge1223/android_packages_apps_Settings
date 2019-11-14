package com.google.android.settings.gestures.assist;

import android.content.Context;
import android.util.AttributeSet;
import androidx.core.content.res.TypedArrayUtils;
import com.android.settings.R;
import com.android.settings.widget.SeekBarPreference;

public class AssistGestureSeekBarPreference extends SeekBarPreference {
    public AssistGestureSeekBarPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        setLayoutResource(R.layout.preference_assist_gesture_slider);
    }

    public AssistGestureSeekBarPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, TypedArrayUtils.getAttr(context, R.attr.seekBarPreferenceStyle, 17957066), 0);
    }
}
