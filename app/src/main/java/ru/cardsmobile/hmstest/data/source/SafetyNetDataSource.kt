package ru.cardsmobile.hmstest.data.source

import com.google.android.gms.safetynet.SafetyNetClient
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.BiFunction

class SafetyNetDataSource(
    private val safetyNetClient: SafetyNetClient,
    private val securityBackendDataSource: SecurityBackendDataSource
) {

    fun getSecurityCheckResult(): Single<String> = Single
        .zip(
            securityBackendDataSource.getNonce(),
            getApiKey(),
            BiFunction { nonce: ByteArray, apiKey: String -> nonce to apiKey }
        )
        .flatMap { (nonce, apiKey) -> requestJwsToken(nonce, apiKey) }
        .flatMap { securityBackendDataSource.validateSafetyNetResponse(it) }

    private fun requestJwsToken(
        nonce: ByteArray,
        apiKey: String
    ): Single<String> = Single
        .create { emitter ->
            safetyNetClient
                .attest(nonce, apiKey)
                .addOnSuccessListener {
                    if (!emitter.isDisposed) {
                        emitter.onSuccess(it.jwsResult)
                    }
                }
                .addOnFailureListener {
                    emitter.tryOnError(it)
                }
        }

    private fun getApiKey(): Single<String> = Single
        .fromCallable { "AIzaSyB60atdb0eAckEwV3dHrRERbo34FeiJN38" }
}
