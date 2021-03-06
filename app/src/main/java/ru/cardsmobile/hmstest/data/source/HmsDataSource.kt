package ru.cardsmobile.hmstest.data.source

import android.util.Log
import com.huawei.agconnect.config.AGConnectServicesConfig
import com.huawei.hms.aaid.HmsInstanceId
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.BehaviorSubject

class HmsDataSource(
    private val hmsInstanceId: Lazy<HmsInstanceId>,
    private val agConnectServicesConfig: Lazy<AGConnectServicesConfig>
) {

    private val currentPushToken = BehaviorSubject.create<String>()

    fun getHmsPushToken(): Single<String> = Maybe
        .merge(
            getHmsPushTokenFromSdk(),
            currentPushToken.firstElement()
        )
        .firstOrError()

    fun onPushTokenUpdated(token: String): Completable = Completable
        .fromCallable { currentPushToken.onNext(token) }

    private fun getHmsPushTokenFromSdk(): Maybe<String> = Maybe
        .fromCallable<String> {
            val appId = agConnectServicesConfig.value.getString("client/app_id")
            hmsInstanceId.value.getToken(appId, "HCM").takeIf { it.isNotEmpty() }
        }
        .doOnSuccess { Log.w(LOG_TAG, "get token from singleton success: $it") }
        .doOnComplete { Log.w(LOG_TAG, "unable to get token from singleton") }
        .doOnError { Log.w(LOG_TAG, "get token from singleton failed", it) }
        .onErrorComplete()

    private companion object {
        const val LOG_TAG = "HmsDataSource"
    }
}
