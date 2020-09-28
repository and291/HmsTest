package ru.cardsmobile.hmstest.domain.repository

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.cardsmobile.hmstest.domain.entity.MobileServiceType

interface PushRepository {

    fun getPushToken(mobileServiceType: MobileServiceType): Single<String>

    fun onPushTokenUpdated(
        token: String,
        mobileServiceType: MobileServiceType
    ): Completable
}
