package com.google.android.settings.experiments;

import android.content.ContentProviderClient;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class PhenotypeProxy {
    private static final Uri PROXY_AUTHORITY = new Uri.Builder().scheme("content").authority("com.google.android.settings.intelligence.provider.experimentflags").build();


    public static boolean getFlagByPackageAndKey(Context context, String str, String str2, boolean z) {
        Bundle bundle = new Bundle();
        bundle.putString("package_name", str);
        bundle.putString("key", str2);
        Bundle bundle2 = null;
        Throwable  r4 = null;
        try {
            ContentProviderClient acquireUnstableContentProviderClient = context.getContentResolver().acquireUnstableContentProviderClient(PROXY_AUTHORITY);
            bundle2 = acquireUnstableContentProviderClient.call("getBooleanForPackageAndKey", (String) null, bundle);
            if (acquireUnstableContentProviderClient != null) {
                acquireUnstableContentProviderClient.close();
            }
        } catch (Exception e) {
            Log.e("PhenotypeProxy", "Failed to query experiment provider", e);
        } catch (Throwable th) {
            r4.addSuppressed(th);
        }
        if (bundle2 == null) {
            return z;
        }
        return bundle2.getBoolean("value", z);
    }
}
