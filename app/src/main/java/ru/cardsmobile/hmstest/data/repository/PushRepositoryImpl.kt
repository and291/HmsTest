package ru.cardsmobile.hmstest.data.repository

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.cardsmobile.hmstest.data.source.FcmDataSource
import ru.cardsmobile.hmstest.data.source.HmsDataSource
import ru.cardsmobile.hmstest.domain.entity.MobileServiceType
import ru.cardsmobile.hmstest.domain.entity.MobileServiceType.Google
import ru.cardsmobile.hmstest.domain.entity.MobileServiceType.Huawei
import ru.cardsmobile.hmstest.domain.repository.PushRepository

class PushRepositoryImpl(
    private val fcmDataSource: FcmDataSource,
    private val hmsDataSource: HmsDataSource
) : PushRepository {

    override fun getPushToken(
        mobileServiceType: MobileServiceType
    ): Single<String> = Single
        .just(mobileServiceType)
        .flatMap {
            @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
            when (it) {
                Google -> fcmDataSource.getFcmPushToken()
                Huawei -> hmsDataSource.getHmsPushToken()
            }
        }
        .subscribeOn(Schedulers.io())

    override fun onPushTokenUpdated(
        token: String,
        mobileServiceType: MobileServiceType
    ): Completable = Single
        .just(mobileServiceType)
        .flatMapCompletable {
            @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
            when (it) {
                Google -> throw NotImplementedError()
                Huawei -> hmsDataSource.onPushTokenUpdated(token)
            }
        }
        .subscribeOn(Schedulers.io())
}
