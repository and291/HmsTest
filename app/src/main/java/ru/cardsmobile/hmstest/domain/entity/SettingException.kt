package ru.cardsmobile.hmstest.domain.entity

import android.app.Activity
import com.huawei.hms.common.ResolvableApiException

internal typealias HmsResolvableApiException = ResolvableApiException
internal typealias GmsResolvableApiException = com.google.android.gms.common.api.ResolvableApiException

sealed class SettingException: Exception() {

    class HuaweiSettingException(private val exp: HmsResolvableApiException): SettingException() {
        override fun startResolutionForResult(activity: Activity, requestCode: Int) {
            exp.startResolutionForResult(activity, requestCode)
        }
    }

    class GoogleSettingException(private val exp: GmsResolvableApiException): SettingException() {
        override fun startResolutionForResult(activity: Activity, requestCode: Int) {
            exp.startResolutionForResult(activity, requestCode)
        }
    }

    abstract fun startResolutionForResult(activity: Activity, requestCode: Int)
}
