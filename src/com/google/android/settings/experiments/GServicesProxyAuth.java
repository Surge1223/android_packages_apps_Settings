package com.google.android.settings.experiments;

import android.content.ContentResolver;
import android.os.Bundle;
import java.util.concurrent.Callable;

public class GServicesProxyAuth implements Callable {
    private final ContentResolver f$0;
    private final String f$1;
    private final Bundle f$2;

    public GServicesProxyAuth(ContentResolver contentResolver, String str, Bundle bundle) {
        this.f$0 = contentResolver;
        this.f$1 = str;
        this.f$2 = bundle;
    }

    public final Object call() {
        return this.f$0.call(GServicesProxy.PROXY_AUTHORITY, this.f$1, (String) null, this.f$2);
    }
}
