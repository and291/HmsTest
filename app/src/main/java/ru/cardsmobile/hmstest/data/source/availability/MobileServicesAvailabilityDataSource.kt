package ru.cardsmobile.hmstest.data.source.availability

import io.reactivex.rxjava3.core.Maybe
import ru.cardsmobile.hmstest.domain.entity.MobileServiceEnvironment

interface MobileServicesAvailabilityDataSource {

    fun getState(): Maybe<MobileServiceEnvironment>
}
