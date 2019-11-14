package com.google.android.settings.connecteddevice.dock;

import java.util.function.Predicate;


public  class SavedDockUpdaterDevice implements Predicate {
    private  SavedDockUpdater f$0;

    public  SavedDockUpdaterDevice(SavedDockUpdater savedDockUpdater) {
        this.f$0 = savedDockUpdater;
    }

    public final boolean test(Object obj) {
        return this.f$0.hasDeviceBeenRemoved((String) obj);
    }
}
