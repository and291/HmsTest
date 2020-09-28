package ru.cardsmobile.hmstest.presentation.map.huawei

import com.huawei.hms.maps.UiSettings
import ru.cardsmobile.hmstest.presentation.map.UiSettingsWrapper

class HuaweiUiSettings(
    private val uiSettings: UiSettings
) : UiSettingsWrapper {

    override var isZoomControlsEnabled: Boolean
        get() = uiSettings.isZoomControlsEnabled
        set(value) {
            uiSettings.isZoomControlsEnabled = value
        }
}
