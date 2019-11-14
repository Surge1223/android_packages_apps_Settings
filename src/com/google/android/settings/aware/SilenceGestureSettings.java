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

public class SilenceGestureSettings extends DashboardFragment {
    public static final Indexable.SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new C15671();

    class C15671 extends BaseSearchIndexProvider {
        C15671() {
        }

        public List<SearchIndexableResource> getXmlResourcesToIndex(Context context, boolean z) {
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.xmlResId = R.xml.silence_gesture_settings;
            return Arrays.asList(new SearchIndexableResource[]{searchIndexableResource});
        }
    }

    public String getLogTag() {
        return "SilenceGestureSettings";
    }

    public int getMetricsCategory() {
        return 1625;
    }

    public int getPreferenceScreenResId() {
        return R.xml.silence_gesture_settings;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        ((SilenceGestureFooterPreferenceController) use(SilenceGestureFooterPreferenceController.class)).setFooterMixin(this.mFooterPreferenceMixin);
    }

    /* access modifiers changed from: protected */
    public List<AbstractPreferenceController> createPreferenceControllers(Context context) {
        SilenceGestureFooterPreferenceController silenceGestureFooterPreferenceController = new SilenceGestureFooterPreferenceController(context);
        Lifecycle settingsLifecycle = getSettingsLifecycle();
        if (settingsLifecycle != null) {
            settingsLifecycle.addObserver(silenceGestureFooterPreferenceController);
        }
        return Arrays.asList(new AbstractPreferenceController[]{silenceGestureFooterPreferenceController});
    }
}
