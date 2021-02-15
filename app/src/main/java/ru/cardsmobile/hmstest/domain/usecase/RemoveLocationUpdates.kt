package ru.cardsmobile.hmstest.domain.usecase

import ru.cardsmobile.hmstest.domain.repository.LocationRepository
import ru.cardsmobile.hmstest.domain.repository.LocationUpdateListener

class RemoveLocationUpdates(
    private val locationRepository: LocationRepository,
    private val selectMobileServiceType: SelectMobileServiceType
) {

    operator fun invoke(
        updateListener: LocationUpdateListener,
        operationCallback: (Result<Unit>) -> Unit
    ) {
        runCatching { selectMobileServiceType(SelectMobileServiceType.Case.Location).blockingGet() }
            .map {
                locationRepository.removeLocationUpdates(
                    mobileServiceType = it,
                    updateListener = updateListener,
                    operationCallback = operationCallback
                )
            }
    }
}