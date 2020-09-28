package ru.cardsmobile.hmstest.domain.repository

import io.reactivex.rxjava3.core.Single
import ru.cardsmobile.hmstest.domain.entity.MobileServiceType

interface SecurityRepository {

    fun getSecurityCheckResult(mobileServiceType: MobileServiceType): Single<String>
}
