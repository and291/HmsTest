package ru.cardsmobile.hmstest.data.source

import com.huawei.agconnect.config.AGConnectServicesConfig
import com.huawei.hms.support.api.safetydetect.SafetyDetectClient
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.BiFunction

class SafetyDetectDataSource(
    private val safetyDetectClient: SafetyDetectClient,
    private val agConnectServicesConfig: AGConnectServicesConfig,
    private val securityBackendDataSource: SecurityBackendDataSource
) {

    fun getSecurityCheckResult(): Single<String> = Single
        .zip(
            securityBackendDataSource.getNonce(),
            getAppId(),
            BiFunction { nonce: ByteArray, appId: String -> nonce to appId }
        )
        .flatMap { (nonce, appId) -> makeSafetyNetCall(nonce, appId) }
        .flatMap { securityBackendDataSource.validateSafetyDetectResponse(it) }

    private fun makeSafetyNetCall(
        nonce: ByteArray,
        appId: String
    ): Single<String> = Single
        .create { emitter ->
            safetyDetectClient
                .sysIntegrity(nonce, appId)
                .addOnSuccessListener {
                    if (!emitter.isDisposed) {
                        emitter.onSuccess(it.result)
                    }
                }
                .addOnFailureListener {
                    emitter.tryOnError(it)
                }
        }

    private fun getAppId(): Single<String> = Single
        .fromCallable {
            agConnectServicesConfig.getString("client/app_id")
        }
}
