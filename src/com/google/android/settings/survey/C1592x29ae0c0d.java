package com.google.android.settings.survey;

import com.google.android.settings.survey.SurveyFeatureProviderImpl;
import java.util.concurrent.Callable;

public final  class C1592x29ae0c0d implements Callable {
    private  SurveyFeatureProviderImpl.SurveyProviderLoader f$0;

    public  C1592x29ae0c0d(SurveyFeatureProviderImpl.SurveyProviderLoader surveyProviderLoader) {
        this.f$0 = surveyProviderLoader;
    }

    public final Object call() {
        try {
            return this.f$0.lambda$getAdsId$0$SurveyFeatureProviderImpl$SurveyProviderLoader();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
