package ru.cardsmobile.hmstest.domain.repository

import io.reactivex.rxjava3.core.Single
import ru.cardsmobile.hmstest.domain.entity.MobileServiceEnvironment

interface MobileServicesRepository {

    fun getAvailableServices(): Single<Set<MobileServiceEnvironment>>
}
