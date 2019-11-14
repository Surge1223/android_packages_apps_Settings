package com.google.android.settings.gestures.assist;

import android.animation.TimeAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.android.settings.R;

class IndicatorDrawable extends Drawable {
    private Context mContext;
    private final Handler mHandler = new C15821(Looper.getMainLooper());
    private Paint mPaint = new Paint();
    private float mProgress;
    private boolean mReversed;
    public long mTime;
    public TimeAnimator mTimeAnimator;

    class C15821 extends Handler {
        C15821(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                IndicatorDrawable.this.mTimeAnimator.start();
            } else if (i == 2) {
                IndicatorDrawable.this.mTimeAnimator.end();
            }
        }
    }

    class C15832 implements TimeAnimator.TimeListener {
        C15832() {
        }

        public void onTimeUpdate(TimeAnimator timeAnimator, long j, long j2) {
            long unused = IndicatorDrawable.this.mTime = j;
            if (j >= 150) {
                timeAnimator.end();
            }
            IndicatorDrawable.this.invalidateSelf();
        }
    }

    public int getOpacity() {
        return 0;
    }

    public void setAlpha(int i) {
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public IndicatorDrawable(Context context, boolean z) {
        this.mContext = context;
        this.mReversed = z;
        this.mTimeAnimator = new TimeAnimator();
        this.mTimeAnimator.setTimeListener(new C15832());
    }

    public void draw(Canvas canvas) {
        float f;
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(this.mContext.getResources().getColor(R.color.active_edge_indicator));
        this.mPaint.setAlpha(63);
        int height = canvas.getHeight() / 2;
        int height2 = canvas.getHeight();
        Path path = new Path();
        if (this.mReversed) {
            path.moveTo((float) canvas.getWidth(), (float) height);
        } else {
            path.moveTo(0.0f, (float) height);
        }
        float width = ((float) canvas.getWidth()) * this.mProgress;
        if (this.mTimeAnimator.isRunning()) {
            float f2 = 1.0f - (((float) this.mTime) / 150.0f);
            this.mPaint.setAlpha((int) (63.0f * f2));
            f = ((float) canvas.getWidth()) * f2;
        } else {
            f = width;
        }
        if (this.mReversed) {
            float f3 = (float) height2;
            path.cubicTo(((float) canvas.getWidth()) - f, ((float) height) + 150.0f, ((float) canvas.getWidth()) - f, f3 - 150.0f, (float) canvas.getWidth(), f3);
        } else {
            float f4 = (float) height2;
            path.cubicTo(f, ((float) height) + 150.0f, f, f4 - 150.0f, 0.0f, f4);
        }
        canvas.drawPath(path, this.mPaint);
    }

    public void onGestureProgress(float f) {
        this.mHandler.sendEmptyMessage(2);
        this.mProgress = f;
        invalidateSelf();
    }

    public void onGestureDetected() {
        this.mProgress = 0.0f;
        this.mHandler.sendEmptyMessage(1);
    }
}
