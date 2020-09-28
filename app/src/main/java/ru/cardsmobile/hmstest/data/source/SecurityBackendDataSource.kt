package ru.cardsmobile.hmstest.data.source

import android.util.Base64
import io.reactivex.rxjava3.core.Single

/**
 * Mocks security backend server.
 * This implementation should not be used in production
 */
class SecurityBackendDataSource {

    fun getNonce(): Single<ByteArray> = Single
        .fromCallable { "HelloWorld! ${System.currentTimeMillis()}".toByteArray() }

    fun validateSafetyNetResponse(
        jwsResult: String
    ): Single<String> = getJwsTokenPayload(jwsResult)

    fun validateSafetyDetectResponse(
        jwsResult: String
    ): Single<String> = getJwsTokenPayload(jwsResult)

    private fun getJwsTokenPayload(
        jwsResult: String
    ): Single<String> = Single
        .fromCallable {
            jwsResult
                .split(".")
                .dropLastWhile(String::isEmpty)
                .takeIf { it.size == 3 }
                ?.let {
                    val decodedBytes = Base64.decode(it[1], Base64.NO_WRAP)
                    String(decodedBytes)
                }
                ?: throw IllegalArgumentException()
        }
}
