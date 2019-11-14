package com.google.android.settings.aware;

import android.content.Context;
import android.provider.SearchIndexableResource;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.search.Indexable;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;

import java.util.Arrays;
import java.util.List;

public class SkipGestureSettings extends DashboardFragment {
    public static final Indexable.SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new C15681();

    public String getLogTag() {
        return "SkipGestureSettings";
    }

    public int getMetricsCategory() {
        return 1624;
    }

    public int getPreferenceScreenResId() {
        return R.xml.skip_gesture_settings;
    }

    public void onAttach(Context context) {
        super.onAttach( context );
        ((SkipGestureFooterPreferenceController) use( SkipGestureFooterPreferenceController.class )).setFooterMixin( this.mFooterPreferenceMixin );
    }

    public List<AbstractPreferenceController> createPreferenceControllers(Context context) {
        SkipGestureFooterPreferenceController skipGestureFooterPreferenceController = new SkipGestureFooterPreferenceController( context );
        Lifecycle settingsLifecycle = getSettingsLifecycle();
        if (settingsLifecycle != null) {
            settingsLifecycle.addObserver( skipGestureFooterPreferenceController );
        }
        return Arrays.asList( new AbstractPreferenceController[]{skipGestureFooterPreferenceController} );
    }

    class C15681 extends BaseSearchIndexProvider {
        C15681() {
        }

        public List<SearchIndexableResource> getXmlResourcesToIndex(Context context, boolean z) {
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource( context );
            searchIndexableResource.xmlResId = R.xml.skip_gesture_settings;
            return Arrays.asList( new SearchIndexableResource[]{searchIndexableResource} );
        }
    }
}
