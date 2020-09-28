package ru.cardsmobile.hmstest.data.repository

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import ru.cardsmobile.hmstest.data.source.availability.MobileServicesAvailabilityDataSource
import ru.cardsmobile.hmstest.domain.repository.MobileServicesRepository
import ru.cardsmobile.hmstest.domain.entity.MobileServiceEnvironment

class MobileServicesRepositoryImpl(
    private val mobileServices: List<MobileServicesAvailabilityDataSource>
) : MobileServicesRepository {

    override fun getAvailableServices(): Single<Set<MobileServiceEnvironment>> = Observable
        .fromIterable(mobileServices)
        .flatMapMaybe { it.getState() }
        .toList()
        .map { it.toSet() }
}
