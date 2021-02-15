package ru.cardsmobile.hmstest.domain.usecase

import ru.cardsmobile.hmstest.domain.entity.LocationSettings
import ru.cardsmobile.hmstest.domain.entity.LocationUpdateParams
import ru.cardsmobile.hmstest.domain.entity.Priority
import ru.cardsmobile.hmstest.domain.repository.LocationRepository

class CheckLocationSettings(
    private val locationRepository: LocationRepository,
    private val selectMobileServiceType: SelectMobileServiceType
) {

    operator fun invoke(callback: (Result<Unit>) -> Unit) {
        runCatching { selectMobileServiceType(SelectMobileServiceType.Case.Location).blockingGet() }
            .map {
                locationRepository.checkLocationSettings(
                    mobileServiceType = it,
                    callback = callback,
                    settings = LocationSettings(
                        listOf(
                            LocationUpdateParams(
                                interval = 10000,
                                priority = Priority.PRIORITY_HIGH_ACCURACY
                            )
                        ),
                        isAlwaysShow = true
                    )
                )
            }
    }
}
