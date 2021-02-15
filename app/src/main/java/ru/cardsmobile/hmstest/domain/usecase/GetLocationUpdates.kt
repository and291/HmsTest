package ru.cardsmobile.hmstest.domain.usecase

import ru.cardsmobile.hmstest.domain.entity.LocationUpdateParams
import ru.cardsmobile.hmstest.domain.entity.Priority
import ru.cardsmobile.hmstest.domain.repository.LocationRepository
import ru.cardsmobile.hmstest.domain.repository.LocationUpdateListener

class GetLocationUpdates(
    private val locationRepository: LocationRepository,
    private val selectMobileServiceType: SelectMobileServiceType
) {

    operator fun invoke(
        updateListener: LocationUpdateListener,
        operationCallback: (Result<Unit>) -> Unit
    ) {
        runCatching { selectMobileServiceType(SelectMobileServiceType.Case.Location).blockingGet() }
            .map {
                locationRepository.getLocationUpdates(
                    mobileServiceType = it,
                    params = LocationUpdateParams(
                        interval = 10000,
                        priority = Priority.PRIORITY_HIGH_ACCURACY
                    ),
                    updateListener = updateListener,
                    operationCallback = operationCallback
                )
            }
    }
}