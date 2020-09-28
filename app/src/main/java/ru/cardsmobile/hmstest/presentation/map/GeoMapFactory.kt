package ru.cardsmobile.hmstest.presentation.map

import com.google.android.gms.maps.GoogleMap
import com.huawei.hms.maps.HuaweiMap
import ru.cardsmobile.hmstest.presentation.map.google.GoogleGeoMap
import ru.cardsmobile.hmstest.presentation.map.google.GoogleUiSettings
import ru.cardsmobile.hmstest.presentation.map.huawei.HuaweiGeoMap
import ru.cardsmobile.hmstest.presentation.map.huawei.HuaweiUiSettings

class GeoMapFactory {

    fun create(huaweiMap: HuaweiMap): GeoMap {
        val huaweiUiSettings = HuaweiUiSettings(huaweiMap.uiSettings)
        return HuaweiGeoMap(huaweiUiSettings)
    }

    fun create(googleMap: GoogleMap): GeoMap {
        val googleUiSettings = GoogleUiSettings(googleMap.uiSettings)
        return GoogleGeoMap(googleUiSettings)
    }
}
