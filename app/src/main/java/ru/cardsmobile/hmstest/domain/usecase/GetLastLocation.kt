package ru.cardsmobile.hmstest.domain.usecase

import android.location.Location
import ru.cardsmobile.hmstest.domain.repository.LocationRepository

class GetLastLocation(
    private val locationRepository: LocationRepository,
    private val selectMobileServiceType: SelectMobileServiceType
) {

    operator fun invoke(callback: (Result<Location?>) -> Unit) {
        runCatching { selectMobileServiceType(SelectMobileServiceType.Case.Location).blockingGet() }
            .map {
                locationRepository.getLastLocation(
                    mobileServiceType = it,
                    callback = callback
                )
            }
    }
}
