package com.google.android.settings.connecteddevice.dock;

import android.content.ContentProviderClient;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.connecteddevice.DevicePreferenceCallback;
import com.android.settings.connecteddevice.dock.DockUpdater;
import com.android.settings.widget.SingleTargetGearPreference;
import com.google.android.settings.connecteddevice.dock.DockAsyncQueryHandler;
import java.util.List;

public class ConnectedDockUpdater implements DockUpdater, DockAsyncQueryHandler.OnQueryListener {
    private final DockAsyncQueryHandler mAsyncQueryHandler;
    private final ConnectedDockObserver mConnectedDockObserver;
    private final Context mContext;
    private final DevicePreferenceCallback mDevicePreferenceCallback;
    private String mDockId = null;
    private String mDockName = null;
    @VisibleForTesting
    SingleTargetGearPreference mDockPreference = null;
    private final Uri mDockProviderUri;
    @VisibleForTesting
    boolean mIsObserverRegistered;
    private Context mPreferenceContext = null;

    private class ConnectedDockObserver extends ContentObserver {
        ConnectedDockObserver(Handler handler) {
            super(handler);
        }

        public void onChange(boolean z) {
            super.onChange(z);
            ConnectedDockUpdater.this.forceUpdate();
        }
    }

    public ConnectedDockUpdater(Context context, DevicePreferenceCallback devicePreferenceCallback) {
        this.mContext = context;
        this.mDevicePreferenceCallback = devicePreferenceCallback;
        this.mDockProviderUri = DockContract.DOCK_PROVIDER_CONNECTED_URI;
        this.mConnectedDockObserver = new ConnectedDockObserver(new Handler(Looper.getMainLooper()));
        this.mAsyncQueryHandler = new DockAsyncQueryHandler(this.mContext.getContentResolver());
        this.mAsyncQueryHandler.setOnQueryListener(this);
    }

    public void registerCallback() {
        ContentProviderClient acquireContentProviderClient = this.mContext.getContentResolver().acquireContentProviderClient(this.mDockProviderUri);
        if (acquireContentProviderClient != null) {
            acquireContentProviderClient.release();
            this.mContext.getContentResolver().registerContentObserver(this.mDockProviderUri, false, this.mConnectedDockObserver);
            this.mIsObserverRegistered = true;
            forceUpdate();
        }
    }

    public void unregisterCallback() {
        if (this.mIsObserverRegistered) {
            this.mContext.getContentResolver().unregisterContentObserver(this.mConnectedDockObserver);
            this.mIsObserverRegistered = false;
        }
    }

    public void forceUpdate() {
        this.mAsyncQueryHandler.startQuery(1, this.mContext, this.mDockProviderUri, DockContract.DOCK_PROJECTION, (String) null, (String[]) null, (String) null);
    }

    public void setPreferenceContext(Context context) {
        this.mPreferenceContext = context;
    }

    public void onQueryComplete(int i, List<DockDevice> list) {
        if (list == null || list.isEmpty()) {
            SingleTargetGearPreference singleTargetGearPreference = this.mDockPreference;
            if (singleTargetGearPreference != null && singleTargetGearPreference.isVisible()) {
                this.mDockPreference.setVisible(false);
                this.mDevicePreferenceCallback.onDeviceRemoved(this.mDockPreference);
                return;
            }
            return;
        }
        DockDevice dockDevice = list.get(0);
        this.mDockId = dockDevice.getId();
        this.mDockName = dockDevice.getName();
        updatePreference();
    }

    private void updatePreference() {
        if (this.mDockPreference == null) {
            initPreference();
        }
        if (!TextUtils.isEmpty(this.mDockName)) {
            this.mDockPreference.setTitle((CharSequence) this.mDockName);
            if (!TextUtils.isEmpty(this.mDockId)) {
                this.mDockPreference.setIntent(DockContract.buildDockSettingIntent(this.mDockId));
                this.mDockPreference.setSelectable(true);
            }
            if (!this.mDockPreference.isVisible()) {
                this.mDockPreference.setVisible(true);
                this.mDevicePreferenceCallback.onDeviceAdded(this.mDockPreference);
            }
        } else if (this.mDockPreference.isVisible()) {
            this.mDockPreference.setVisible(false);
            this.mDevicePreferenceCallback.onDeviceRemoved(this.mDockPreference);
        }
    }
    @VisibleForTesting
    public void initPreference() {
        if (this.mDockPreference == null) {
            Context context = this.mPreferenceContext;
            if (context != null) {
                this.mDockPreference = new SingleTargetGearPreference(context, (AttributeSet) null);
                this.mDockPreference.setIcon((int) R.drawable.ic_dock_24dp);
                this.mDockPreference.setSummary((CharSequence) this.mContext.getString(R.string.dock_summary_charging_phone));
                this.mDockPreference.setSelectable(false);
                this.mDockPreference.setVisible(false);
                return;
            }
            throw new IllegalStateException("The preference context is null");
        }
    }
}
