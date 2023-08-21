/*
 * This is the source code of OctoGram for Android v.2.0.x
 * It is licensed under GNU GPL v. 2 or later.
 * You should have received a copy of the license in this archive (see LICENSE).
 *
 * Copyright OctoGram, 2023.
 */

package it.octogram.android.preferences.ui;

import android.content.Context;
import android.util.Pair;

import it.octogram.android.preferences.rows.impl.ListRow;
import it.octogram.android.preferences.ui.custom.AllowExperimentalBottomSheet;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;
import org.telegram.ui.ActionBar.BaseFragment;

import java.util.ArrayList;

import it.octogram.android.OctoConfig;
import it.octogram.android.preferences.OctoPreferences;
import it.octogram.android.preferences.PreferencesEntry;
import it.octogram.android.preferences.rows.impl.HeaderRow;
import it.octogram.android.preferences.rows.impl.SliderChooseRow;
import it.octogram.android.preferences.rows.impl.SwitchRow;

public class OctoExperimentsUI implements PreferencesEntry {

    @Override
    public OctoPreferences getPreferences(BaseFragment fragment, Context context) {
        if (OctoConfig.INSTANCE.experimentsEnabled.getValue()) {
            if (!OctoConfig.INSTANCE.alternativeNavigation.getValue() &&
                    !OctoConfig.INSTANCE.uploadBoost.getValue() &&
                    !OctoConfig.INSTANCE.downloadBoost.getValue() &&
                    OctoConfig.INSTANCE.photoResolution.getValue() != 1) {
                OctoConfig.INSTANCE.toggleBooleanSetting(OctoConfig.INSTANCE.experimentsEnabled);
            }
        }
        return OctoPreferences.builder(LocaleController.getString("Experiments", R.string.Experiments))
                .sticker(context, R.raw.utyan_experiments, true, LocaleController.formatString("OctoExperimentsSettingsHeader", R.string.OctoExperimentsSettingsHeader))
                .category(LocaleController.getString("ExperimentalSettings", R.string.ExperimentalSettings), category -> {
                    category.row(new SwitchRow.SwitchRowBuilder()
                            .onClick(() -> checkExperimentsEnabled(fragment, context))
                            .preferenceValue(OctoConfig.INSTANCE.alternativeNavigation)
                            .title(LocaleController.getString("AlternativeNavigation", R.string.AlternativeNavigation))
                            .description(LocaleController.getString("AlternativeNavigation_Desc", R.string.AlternativeNavigation_Desc))
                            .requiresRestart(true)
                            .build());
                    category.row(new SwitchRow.SwitchRowBuilder()
                            .onClick(() -> checkExperimentsEnabled(fragment, context))
                            .preferenceValue(OctoConfig.INSTANCE.uploadBoost)
                            .title(LocaleController.getString("UploadBoost", R.string.UploadBoost))
                            .build());
                    category.row(new SwitchRow.SwitchRowBuilder()
                            .onClick(() -> checkExperimentsEnabled(fragment, context))
                            .preferenceValue(OctoConfig.INSTANCE.downloadBoost)
                            .title(LocaleController.getString("DownloadBoost", R.string.DownloadBoost))
                            .build());
                    category.row(new HeaderRow(LocaleController.getString("DownloadBoostType", R.string.DownloadBoostType), OctoConfig.INSTANCE.downloadBoost));
                    category.row(new SliderChooseRow.SliderChooseRowBuilder()
                            .options(new ArrayList<>() {{
                                add(new Pair<>(0, LocaleController.getString("Default", R.string.Default)));
                                add(new Pair<>(1, LocaleController.getString("Fast", R.string.Fast)));
                                add(new Pair<>(2, LocaleController.getString("Extreme", R.string.Extreme)));
                            }})
                            .preferenceValue(OctoConfig.INSTANCE.downloadBoostValue)
                            .showIf(OctoConfig.INSTANCE.downloadBoost)
                            .build());
                    category.row(new ListRow.ListRowBuilder()
                            .onClick(() -> checkExperimentsEnabled(fragment, context))
                            .options(new ArrayList<>() {{
                                add(new Pair<>(OctoConfig.PhotoResolution.LOW, LocaleController.getString(R.string.ResolutionLow)));
                                add(new Pair<>(OctoConfig.PhotoResolution.DEFAULT, LocaleController.getString(R.string.ResolutionMedium)));
                                add(new Pair<>(OctoConfig.PhotoResolution.HIGH, LocaleController.getString(R.string.ResolutionHigh)));
                                add(new Pair<>(OctoConfig.PhotoResolution.EXTREME, LocaleController.getString(R.string.ResolutionVeryHigh)));
                                add(new Pair<>(OctoConfig.PhotoResolution.UHD, LocaleController.getString(R.string.ResolutionUltraHigh)));
                            }})
                            .currentValue(OctoConfig.INSTANCE.photoResolution)
                            .title(LocaleController.getString("PhotoResolution", R.string.PhotoResolution))
                            .build());
                })
                .build();
    }

    private boolean checkExperimentsEnabled(BaseFragment fragment, Context context) {
        if (OctoConfig.INSTANCE.experimentsEnabled.getValue())
            return true;

        // create OK/Cancel dialog
        /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(LocaleController.getString("OctoExperimentsDialogTitle", R.string.OctoExperimentsDialogTitle));
        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), (dialog, which) -> {
            OctoConfig.INSTANCE.toggleBooleanSetting(OctoConfig.INSTANCE.experimentsEnabled);
        });
        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), (dialog, which) -> {
            dialog.dismiss();
        });
        builder.setMessage(LocaleController.getString("OctoExperimentsDialogMessage", R.string.OctoExperimentsDialogMessage));
        fragment.showDialog(builder.create());
        */
        new AllowExperimentalBottomSheet(context).show();

        return OctoConfig.INSTANCE.experimentsEnabled.getValue();
    }

}
