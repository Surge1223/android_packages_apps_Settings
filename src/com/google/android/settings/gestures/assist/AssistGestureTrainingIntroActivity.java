package com.google.android.settings.gestures.assist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.android.settings.SetupWizardUtils;
import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupcompat.util.WizardManagerHelper;
import com.google.android.setupdesign.GlifLayout;
import com.android.settings.R;

public class AssistGestureTrainingIntroActivity extends AssistGestureTrainingBase {
    private static final String FROM_ACCIDENTAL_TRIGGER_CLASS = "com.google.android.settings.gestures.assist.AssistGestureTrainingIntroActivity";

    public int getMetricsCategory() {
        return 991;
    }

    public void onCreate(Bundle bundle) {
        setTheme(SetupWizardUtils.getTheme(getIntent()));
        super.onCreate(bundle);
        setContentView(R.layout.assist_gesture_training_intro_activity);
        FooterBarMixin footerBarMixin = (FooterBarMixin) ((GlifLayout) findViewById(R.id.layout)).getMixin(FooterBarMixin.class);
        FooterButton.Builder builder = new FooterButton.Builder(this);
        builder.setText(R.string.assist_gesture_enrollment_do_it_later);
        builder.setListener(new View.OnClickListener() {
            public final void onClick(View view) {
                AssistGestureTrainingIntroActivity.this.onCancelButtonClicked(view);
            }
        });
        builder.setButtonType(2);
//        builder.setTheme(R.style.SudGlifButton.Secondary);
        footerBarMixin.setSecondaryButton(builder.build());
        FooterButton.Builder builder2 = new FooterButton.Builder(this);
        builder2.setText(R.string.wizard_next);
        builder2.setListener(new View.OnClickListener() {
            public final void onClick(View view) {
                AssistGestureTrainingIntroActivity.this.onNextButtonClicked(view);
            }
        });
        builder2.setButtonType(5);
  //      builder2.setTheme(R.style.SudGlifButton.Primary);
        footerBarMixin.setPrimaryButton(builder2.build());
        FooterButton secondaryButton = footerBarMixin.getSecondaryButton();
        if ("accidental_trigger".contentEquals(getFlowType())) {
            secondaryButton.setText(this, R.string.assist_gesture_enrollment_settings);
        } else {
            secondaryButton.setText(this, R.string.assist_gesture_enrollment_do_it_later);
        }
    }

    public void onNextButtonClicked(View view) {
        startEnrollingActivity();
    }

    public void onCancelButtonClicked(View view) {
        if ("accidental_trigger".contentEquals(getFlowType())) {
            launchAssistGestureSettings();
            return;
        }
        setResult(101);
        finishAndRemoveTask();
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1 && i2 != 0) {
            setResult(i2, intent);
            finishAndRemoveTask();
        }
    }

    private String getFlowType() {
        Intent intent = getIntent();
        if (WizardManagerHelper.isSetupWizardIntent(intent)) {
            return "setup";
        }
        if (WizardManagerHelper.isDeferredSetupWizard(intent)) {
            return "deferred_setup";
        }
        if ("com.google.android.settings.gestures.AssistGestureSuggestion".contentEquals(intent.getComponent().getClassName())) {
            return "settings_suggestion";
        }
        if (FROM_ACCIDENTAL_TRIGGER_CLASS.contentEquals(intent.getComponent().getClassName())) {
            return "accidental_trigger";
        }
        return null;
    }

    private void startEnrollingActivity() {
        Intent intent = new Intent(this, AssistGestureTrainingEnrollingActivity.class);
        intent.putExtra("launched_from", getFlowType());
        SetupWizardUtils.copySetupExtras(getIntent(), intent);
        startActivityForResult(intent, 1);
    }

    public void onGestureProgress(float f, int i) {
        super.onGestureProgress(f, i);
    }

    public void onGestureDetected() {
        clearIndicators();
        startEnrollingActivity();
    }
}

