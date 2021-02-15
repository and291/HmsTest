package com.example.simple_examples.data.source

/**
 * [GoogleMobileServices] - [Google Play Services docs](https://www.android.com/gms)
 *
 * [HuaweiMobileServices] - [Huawei Mobile Services docs](https://developer.huawei.com/consumer/en/hms)
 */
sealed class MobileServiceEnvironment(val updateRequired: Boolean) {
    class GoogleMobileServices(updateRequired: Boolean): MobileServiceEnvironment(updateRequired)
    class HuaweiMobileServices(updateRequired: Boolean): MobileServiceEnvironment(updateRequired)
}