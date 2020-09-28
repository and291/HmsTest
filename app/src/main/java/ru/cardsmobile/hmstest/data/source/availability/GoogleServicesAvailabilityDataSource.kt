package ru.cardsmobile.hmstest.data.source.availability

import android.content.Context
import com.google.android.gms.common.ConnectionResult.*
import com.google.android.gms.common.GoogleApiAvailability
import io.reactivex.rxjava3.core.Maybe
import ru.cardsmobile.hmstest.domain.entity.MobileServiceEnvironment
import ru.cardsmobile.hmstest.domain.entity.MobileServiceEnvironment.GoogleMobileServices

class GoogleServicesAvailabilityDataSource(
    private val appContext: Context,
    private val googleApiAvailability: GoogleApiAvailability
) : MobileServicesAvailabilityDataSource {

    /**
     * [GoogleApiAvailability doc](https://developers.google.com/android/reference/com/google/android/gms/common/GoogleApiAvailability)
     */
    override fun getState(): Maybe<MobileServiceEnvironment> = Maybe
        .fromCallable {
            @Suppress("MoveVariableDeclarationIntoWhen")
            val connectionResult = googleApiAvailability.isGooglePlayServicesAvailable(appContext)
            when (connectionResult) {
                SUCCESS -> GoogleMobileServices(false)
                SERVICE_UPDATING,
                SERVICE_VERSION_UPDATE_REQUIRED -> GoogleMobileServices(true)
                else -> null
            }
        }
}
