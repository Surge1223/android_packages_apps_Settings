package com.google.android.settings.aware;

import android.content.Context;
import android.os.Bundle;
import android.provider.SearchIndexableResource;
import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.overlay.FeatureFactory;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.search.Indexable;
import java.util.Arrays;
import java.util.List;

public class AwareSettings extends DashboardFragment {
    public static final Indexable.SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new C15661();

    class C15661 extends BaseSearchIndexProvider {
        C15661() {
        }

        public List<SearchIndexableResource> getXmlResourcesToIndex(Context context, boolean z) {
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.xmlResId = R.xml.aware_settings;
            return Arrays.asList(new SearchIndexableResource[]{searchIndexableResource});
        }
        public boolean isPageSearchEnabled(Context context) {
            return FeatureFactory.getFactory(context).getAwareFeatureProvider().isSupported(context);
        }
    }

    public String getLogTag() {
        return "AwareSettings";
    }

    public int getMetricsCategory() {
        return 1632;
    }

    public int getPreferenceScreenResId() {
        return R.xml.aware_settings;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        ((AwarePreferenceController) use(AwarePreferenceController.class)).init(this);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mFooterPreferenceMixin.createFooterPreference().setTitle((int) R.string.aware_settings_description);
        Bundle arguments = getArguments();
        if (arguments != null && arguments.getBoolean("show_aware_dialog_enabled", false)) {
            AwareEnabledDialogFragment.show(this, true);
        }
    }
}
