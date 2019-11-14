package com.google.android.settings.widget;

import android.content.res.TypedArray;
import com.android.settings.R;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.view.View;
import android.widget.FrameLayout;

public class AssistGestureTrainingProgressBar extends FrameLayout {
    private View mDoneView;
    private ProgressBar mProgressBar;
    private int mState;
    private TextView mTextView;

    public AssistGestureTrainingProgressBar(Context context) {
        this(context, (AttributeSet) null);
    }

    public AssistGestureTrainingProgressBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, R.style.AssistGestureTrainingProgressBar);
    }

    public AssistGestureTrainingProgressBar(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet);
        this.mState = 0;
        LayoutInflater.from(context).inflate(R.layout.assist_gesture_training_progress_bar, this, true);
        this.mTextView = (TextView) findViewById(R.id.label);
        this.mProgressBar = (ProgressBar) findViewById(R.id.progress);
        this.mDoneView = findViewById(R.id.done);
        refreshViews();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.style.AssistGestureTrainingProgressBar, i, i2);
        this.mTextView.setText(obtainStyledAttributes.getText(0));
        obtainStyledAttributes.recycle();
    }

    public void setState(int i) {
        this.mState = i;
        refreshViews();
    }

    private void refreshViews() {
        int i = 0;
        this.mTextView.setVisibility(this.mState != 2 ? 0 : 8);
        this.mProgressBar.setVisibility(this.mState == 1 ? 0 : 8);
        View view = this.mDoneView;
        if (this.mState != 2) {
            i = 8;
        }
        view.setVisibility(i);
    }

    public void setText(CharSequence charSequence) {
        this.mTextView.setText(charSequence);
    }
}
