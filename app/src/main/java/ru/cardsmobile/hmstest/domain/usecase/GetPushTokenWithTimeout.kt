package ru.cardsmobile.hmstest.domain.usecase

import android.util.Log
import io.reactivex.rxjava3.core.Maybe
import ru.cardsmobile.hmstest.domain.repository.PushRepository
import java.util.concurrent.TimeUnit

class GetPushTokenWithTimeout(
    private val pushRepository: PushRepository,
    private val selectMobileServiceType: SelectMobileServiceType
) {

    operator fun invoke(): Maybe<String> = selectMobileServiceType(SelectMobileServiceType.Case.Push)
        .flatMapSingle { pushRepository.getPushToken(it) }
        .timeout(10, TimeUnit.SECONDS)
        .doOnError { Log.e(LOG_TAG, "Error getting pushToken", it) }
        .onErrorComplete()

    private companion object {
        const val LOG_TAG = "GetPushTokenWithTimeout"
    }
}
