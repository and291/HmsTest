package com.example.simple_examples.data.source

import android.content.Context
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.huawei.hms.api.HuaweiApiAvailability
import com.huawei.hms.api.ConnectionResult as HuaweiConnectionResult

class MobileServicesDetectorImpl(val appContext: Context) : MobileServicesDetector {

    override fun getAvailableServices(): Set<MobileServiceEnvironment> {
        val result = mutableSetOf<MobileServiceEnvironment>()

        getGooglePlayServicesAvailable()?.let { result += it }

        getHuaweiMobileServicesAvailable()?.let { result += it }

        return result
    }

    /**
     * [GoogleApiAvailability doc](https://developers.google.com/android/reference/com/google/android/gms/common/GoogleApiAvailability)
     */
    private fun getGooglePlayServicesAvailable(): MobileServiceEnvironment.GoogleMobileServices? {

        return try {
            when (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(appContext)) {
                ConnectionResult.SUCCESS -> MobileServiceEnvironment.GoogleMobileServices(false)
                ConnectionResult.SERVICE_UPDATING,
                ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED -> MobileServiceEnvironment.GoogleMobileServices(true)

                ConnectionResult.SERVICE_MISSING,
                ConnectionResult.SERVICE_DISABLED,
                ConnectionResult.SERVICE_INVALID -> null

                else -> null
            }
        } catch (e: ClassNotFoundException) {
            return null
        }
    }

    /**
     * [HuaweiApiAvailability doc](https://developer.huawei.com/consumer/en/doc/development/HMS-References/huaweiapiavailability)
     */
    private fun getHuaweiMobileServicesAvailable(): MobileServiceEnvironment.HuaweiMobileServices? {

        return try {
            when (HuaweiApiAvailability.getInstance().isHuaweiMobileServicesAvailable(appContext)) {
                HuaweiConnectionResult.SUCCESS -> MobileServiceEnvironment.HuaweiMobileServices(false)
                HuaweiConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED -> MobileServiceEnvironment.HuaweiMobileServices(true)

                HuaweiConnectionResult.SERVICE_MISSING,
                HuaweiConnectionResult.SERVICE_DISABLED,
                HuaweiConnectionResult.SERVICE_INVALID,
                HuaweiConnectionResult.SERVICE_UNSUPPORTED -> null

                else -> null
            }
        } catch (e: ClassNotFoundException) {
            return null
        }
    }
}