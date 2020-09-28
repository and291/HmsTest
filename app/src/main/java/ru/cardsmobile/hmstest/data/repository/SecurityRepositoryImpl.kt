package ru.cardsmobile.hmstest.data.repository

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.cardsmobile.hmstest.data.source.SafetyDetectDataSource
import ru.cardsmobile.hmstest.data.source.SafetyNetDataSource
import ru.cardsmobile.hmstest.domain.entity.MobileServiceType
import ru.cardsmobile.hmstest.domain.entity.MobileServiceType.Google
import ru.cardsmobile.hmstest.domain.entity.MobileServiceType.Huawei
import ru.cardsmobile.hmstest.domain.repository.SecurityRepository

class SecurityRepositoryImpl(
    private val safetyNetDataSource: SafetyNetDataSource,
    private val safetyDetectDataSource: SafetyDetectDataSource
) : SecurityRepository {

    override fun getSecurityCheckResult(
        mobileServiceType: MobileServiceType
    ): Single<String> = Single
        .just(mobileServiceType)
        .flatMap {
            @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
            when (it) {
                Google -> safetyNetDataSource
                    .getSecurityCheckResult()
                Huawei -> safetyDetectDataSource
                    .getSecurityCheckResult()
            }
        }
        .subscribeOn(Schedulers.io())
}
