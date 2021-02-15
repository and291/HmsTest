package ru.cardsmobile.hmstest.domain.repository

import android.location.Location
import ru.cardsmobile.hmstest.domain.entity.*

internal typealias LocationUpdateListener = (LocationInfo?, AvailabilityInfo?) -> Unit

interface LocationRepository {

    fun getLastLocation(
        mobileServiceType: MobileServiceType,
        callback: (Result<Location?>) -> Unit
    )

    fun getLocationUpdates(
        mobileServiceType: MobileServiceType,
        params: LocationUpdateParams,
        updateListener: LocationUpdateListener,
        operationCallback: (Result<Unit>) -> Unit
    )

    fun removeLocationUpdates(
        mobileServiceType: MobileServiceType,
        updateListener: LocationUpdateListener,
        operationCallback: (Result<Unit>) -> Unit
    )

    fun checkLocationSettings(
        mobileServiceType: MobileServiceType,
        settings: LocationSettings,
        callback: (Result<Unit>) -> Unit
    )
}