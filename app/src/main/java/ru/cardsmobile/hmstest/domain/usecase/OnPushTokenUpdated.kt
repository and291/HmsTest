package ru.cardsmobile.hmstest.domain.usecase

import io.reactivex.rxjava3.core.Completable
import ru.cardsmobile.hmstest.domain.entity.MobileServiceType
import ru.cardsmobile.hmstest.domain.repository.PushRepository

class OnPushTokenUpdated(
    private val pushRepository: PushRepository
) {

    operator fun invoke(
        token: String,
        mobileServiceType: MobileServiceType
    ): Completable = pushRepository
        .onPushTokenUpdated(token, mobileServiceType)
}
