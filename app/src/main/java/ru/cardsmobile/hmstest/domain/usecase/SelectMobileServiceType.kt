package ru.cardsmobile.hmstest.domain.usecase

import io.reactivex.rxjava3.core.Maybe
import ru.cardsmobile.hmstest.domain.entity.MobileServiceEnvironment
import ru.cardsmobile.hmstest.domain.entity.MobileServiceEnvironment.GoogleMobileServices
import ru.cardsmobile.hmstest.domain.entity.MobileServiceEnvironment.HuaweiMobileServices
import ru.cardsmobile.hmstest.domain.entity.MobileServiceType
import ru.cardsmobile.hmstest.domain.repository.MobileServicesRepository

class SelectMobileServiceType(
    private val mobileServicesRepository: MobileServicesRepository
) {

    operator fun invoke(case: Case): Maybe<MobileServiceType> = mobileServicesRepository
        .getAvailableServices()
        .map { excludeEnvironmentsByCase(case, it) }
        .flatMapMaybe { selectEnvironment(it) }
        .map { it.mobileServiceType }

    private fun excludeEnvironmentsByCase(
        case: Case,
        envs: Set<MobileServiceEnvironment>
    ): Iterable<MobileServiceEnvironment> = when (case) {
        Case.Push, Case.Map -> envs
        Case.Security -> envs.filter { !it.isUpdateRequired }
    }

    private fun selectEnvironment(
        envs: Iterable<MobileServiceEnvironment>
    ): Maybe<MobileServiceEnvironment> = Maybe
        .fromCallable<MobileServiceEnvironment> {
            envs.firstOrNull { it is HuaweiMobileServices && (it.emuiApiLevel == null || it.emuiApiLevel >= 21) }
                ?: envs.firstOrNull { it is GoogleMobileServices }
                ?: envs.firstOrNull { it is HuaweiMobileServices }
        }

    enum class Case {
        Push, Map, Security
    }
}
