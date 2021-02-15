package com.example.simple_examples.data.source

import android.util.Base64
import org.json.JSONObject

/**
 * Mocks security backend server.
 * This implementation should not be used in production
 */
class SecurityBackendDataSource {

    fun getNonce(): ByteArray = "HelloWorld! ${System.currentTimeMillis()}".toByteArray()

    fun validateSafetyDetectResponse(
        jwtResult: String
    ) : Boolean = getJwsTokenPayload(jwtResult)

    private fun getJwsTokenPayload(
        jwsResult: String
    ): Boolean =
        jwsResult
            .split(".")
            .dropLastWhile(String::isEmpty)
            .takeIf { it.size == 3 }
            ?.let {
                val decodedBytes = Base64.decode(it[1], Base64.NO_WRAP)
                JSONObject(String(decodedBytes)).getBoolean("basicIntegrity")
            }
            ?: throw IllegalArgumentException()
}
