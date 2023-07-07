/*
 * This is the source code of OctoGram for Android v.2.0.x
 * It is licensed under GNU GPL v. 2 or later.
 * You should have received a copy of the license in this archive (see LICENSE).
 *
 * Copyright OctoGram, 2023.
 */
package it.octogram.android.preferences.tgkit.preference;

import android.content.Context;

import org.telegram.messenger.UserConfig;
import org.telegram.ui.Components.StickerImageView;

import java.util.ArrayList;
import java.util.List;

import it.octogram.android.preferences.tgkit.preference.types.TGKitHeaderRow;
import it.octogram.android.preferences.tgkit.preference.types.TGKitSectionRow;
import it.octogram.android.preferences.tgkit.preference.types.TGKitSettingsCellRow;
import it.octogram.android.preferences.tgkit.preference.types.TGKitStickerHeaderRow;

public class OctoPreferences {

    private final String name;
    private final List<TGKitPreference> preferences;

    private OctoPreferences(String name, List<TGKitPreference> preferences) {
        this.name = name;
        this.preferences = preferences;
    }

    public static OctoPreferencesBuilder builder(String title) {
        return new OctoPreferencesBuilder(title);
    }

    public String getName() {
        return name;
    }

    public List<TGKitPreference> getPreferences() {
        return preferences;
    }

    public static class OctoPreferencesBuilder {
        private final String name;
        private final List<TGKitPreference> preferenceList = new ArrayList<>();

        public OctoPreferencesBuilder(String name) {
            this.name = name;
        }

        public OctoPreferencesBuilder add(TGKitPreference preference) {
            preferenceList.add(preference);
            return this;
        }

        public OctoPreferencesBuilder category(String name, List<TGKitPreference> preferences) {
            preferenceList.add(new TGKitHeaderRow(name));
            preferenceList.addAll(preferences);
            preferenceList.add(new TGKitSectionRow());
            return this;
        }

        public OctoPreferencesBuilder sticker(Context context, String packName, int stickerNum, boolean autoRepeat, String description) {
            StickerImageView rLottieImageView = new StickerImageView(context, UserConfig.selectedAccount);
            rLottieImageView.setStickerPackName(packName);
            rLottieImageView.setStickerNum(stickerNum);
            if (autoRepeat) {
                rLottieImageView.getImageReceiver().setAutoRepeat(1);
            }
            preferenceList.add(new TGKitStickerHeaderRow(rLottieImageView, description));
            preferenceList.add(new TGKitSettingsCellRow());
            preferenceList.add(new TGKitSectionRow());
            return this;
        }


        public OctoPreferences build() {
            return new OctoPreferences(name, preferenceList);
        }
    }

}