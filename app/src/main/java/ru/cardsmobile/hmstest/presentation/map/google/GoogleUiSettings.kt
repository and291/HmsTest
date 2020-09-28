package ru.cardsmobile.hmstest.presentation.map.google

import com.google.android.gms.maps.UiSettings
import ru.cardsmobile.hmstest.presentation.map.UiSettingsWrapper

class GoogleUiSettings(
    private val uiSettings: UiSettings
) : UiSettingsWrapper {

    override var isZoomControlsEnabled: Boolean
        get() = uiSettings.isZoomControlsEnabled
        set(value) {
            uiSettings.isZoomControlsEnabled = value
        }
}
