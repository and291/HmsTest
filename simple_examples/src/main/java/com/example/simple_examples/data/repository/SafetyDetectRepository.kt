package com.example.simple_examples.data.repository

import com.huawei.agconnect.config.AGConnectServicesConfig
import com.huawei.hms.support.api.safetydetect.SafetyDetectClient
import java.util.concurrent.Executor
import com.example.simple_examples.data.repository.SystemIntegrityResult.NOT_ROOTED
import com.example.simple_examples.data.repository.SystemIntegrityResult.ROOTED
import com.example.simple_examples.data.repository.SystemIntegrityResult.FAILED
import com.example.simple_examples.data.source.SecurityBackendDataSource

class SafetyDetectRepository(
    private val safetyDetectClient: SafetyDetectClient,
    private val agConnectServicesConfig: AGConnectServicesConfig,
    private val backendDataSource: SecurityBackendDataSource,
    private val executor: Executor
) {

    fun checkSystemIntegrity(callback: (SystemIntegrityResult) -> Unit) {
        executor.execute {
            val nonce = backendDataSource.getNonce()
            val appId = agConnectServicesConfig.getString("client/app_id")

            safetyDetectClient.sysIntegrity(nonce, appId)
                .addOnSuccessListener {
                    val sysIntegrityResult = backendDataSource.validateSafetyDetectResponse(it.result)
                            .toSystemIntegrityResult()
                    callback(sysIntegrityResult)
                }
                .addOnFailureListener {
                    callback(FAILED)
                }
        }
    }

    private fun Boolean.toSystemIntegrityResult() = if (this) ROOTED else NOT_ROOTED
}

sealed class SystemIntegrityResult {
    object ROOTED : SystemIntegrityResult()
    object NOT_ROOTED : SystemIntegrityResult()
    object FAILED : SystemIntegrityResult()
}
