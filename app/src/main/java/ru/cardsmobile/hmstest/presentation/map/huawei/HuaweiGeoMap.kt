package ru.cardsmobile.hmstest.presentation.map.huawei

import ru.cardsmobile.hmstest.presentation.map.GeoMap
import ru.cardsmobile.hmstest.presentation.map.UiSettingsWrapper

class HuaweiGeoMap(
    private val huaweiUiSettings: HuaweiUiSettings
) : GeoMap {

    override val uiSettings: UiSettingsWrapper
        get() = huaweiUiSettings
}
