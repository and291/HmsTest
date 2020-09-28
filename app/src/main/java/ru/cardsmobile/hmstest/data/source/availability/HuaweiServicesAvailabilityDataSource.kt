package ru.cardsmobile.hmstest.data.source.availability

import android.content.Context
import com.huawei.hms.api.ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED
import com.huawei.hms.api.ConnectionResult.SUCCESS
import com.huawei.hms.api.HuaweiApiAvailability
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import ru.cardsmobile.hmstest.domain.entity.MobileServiceEnvironment
import ru.cardsmobile.hmstest.domain.entity.MobileServiceEnvironment.HuaweiMobileServices

class HuaweiServicesAvailabilityDataSource(
    private val appContext: Context,
    private val huaweiApiAvailability: HuaweiApiAvailability,
    private val emuiDataSource: EmuiDataSource
) : MobileServicesAvailabilityDataSource {

    /**
     * [HuaweiApiAvailability doc](https://developer.huawei.com/consumer/en/doc/development/HMS-References/huaweiapiavailability)
     */
    override fun getState(): Maybe<MobileServiceEnvironment> = Maybe
        .fromCallable<Boolean> {
            @Suppress("MoveVariableDeclarationIntoWhen")
            val connectionResult = huaweiApiAvailability.isHuaweiMobileServicesAvailable(appContext)
            when (connectionResult) {
                SUCCESS -> false
                SERVICE_VERSION_UPDATE_REQUIRED -> true
                else -> null
            }
        }
        .flatMapSingle { appendEmuiApiLevel(it) }

    private fun appendEmuiApiLevel(
        isUpdateRequired: Boolean
    ): Single<HuaweiMobileServices> = emuiDataSource
        .getEmuiApiLevel()
        .map { HuaweiMobileServices(isUpdateRequired, it) }
        .defaultIfEmpty(HuaweiMobileServices(isUpdateRequired, null))
}
