package ru.cardsmobile.hmstest.domain.entity

/**
 * [GoogleMobileServices] - [Google Play Services docs](https://www.android.com/gms)
 *
 * [HuaweiMobileServices] - [Huawei Mobile Services docs](https://developer.huawei.com/consumer/en/hms)
 */
sealed class MobileServiceEnvironment(
    // This field is not included into child data-classes generated methods!
    val mobileServiceType: MobileServiceType
) {
    abstract val isUpdateRequired: Boolean

    data class GoogleMobileServices(
        override val isUpdateRequired: Boolean
    ) : MobileServiceEnvironment(MobileServiceType.Google)

    data class HuaweiMobileServices(
        override val isUpdateRequired: Boolean,
        val emuiApiLevel: Int?
    ) : MobileServiceEnvironment(MobileServiceType.Huawei)
}
