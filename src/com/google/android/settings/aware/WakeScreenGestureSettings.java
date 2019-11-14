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

public class WakeScreenGestureSettings extends DashboardFragment {
    public static final Indexable.SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new C15691();

    class C15691 extends BaseSearchIndexProvider {
        C15691() {
        }

        public List<SearchIndexableResource> getXmlResourcesToIndex(Context context, boolean z) {
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.xmlResId = R.xml.wake_screen_gesture_settings;
            return Arrays.asList(new SearchIndexableResource[]{searchIndexableResource});
        }
    }
    public String getLogTag() {
        return "WakeScreenGestureSettings";
    }

    public int getMetricsCategory() {
        return 1570;
    }
    public int getPreferenceScreenResId() {
        return R.xml.wake_screen_gesture_settings;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        ((WakeScreenGestureFooterPreferenceController) use(WakeScreenGestureFooterPreferenceController.class)).setFooterMixin(this.mFooterPreferenceMixin);
    }

    public List<AbstractPreferenceController> createPreferenceControllers(Context context) {
        WakeScreenGestureFooterPreferenceController wakeScreenGestureFooterPreferenceController = new WakeScreenGestureFooterPreferenceController(context);
        Lifecycle settingsLifecycle = getSettingsLifecycle();
        if (settingsLifecycle != null) {
            settingsLifecycle.addObserver(wakeScreenGestureFooterPreferenceController);
        }
        return Arrays.asList(new AbstractPreferenceController[]{wakeScreenGestureFooterPreferenceController});
    }
}
