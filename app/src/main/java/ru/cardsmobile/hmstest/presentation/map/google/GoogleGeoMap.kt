package ru.cardsmobile.hmstest.presentation.map.google

import ru.cardsmobile.hmstest.presentation.map.GeoMap
import ru.cardsmobile.hmstest.presentation.map.UiSettingsWrapper

class GoogleGeoMap(
    private val googleUiSettings: GoogleUiSettings
) : GeoMap {

    override val uiSettings: UiSettingsWrapper
        get() = googleUiSettings
}
