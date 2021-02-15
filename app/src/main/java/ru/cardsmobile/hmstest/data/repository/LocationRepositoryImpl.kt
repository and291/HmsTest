package ru.cardsmobile.hmstest.data.repository

import android.location.Location
import com.huawei.hms.location.*
import ru.cardsmobile.hmstest.data.source.GmsLocationDataSource
import ru.cardsmobile.hmstest.data.source.HmsLocationDataSource
import ru.cardsmobile.hmstest.domain.entity.*
import ru.cardsmobile.hmstest.domain.entity.MobileServiceType.Google
import ru.cardsmobile.hmstest.domain.entity.MobileServiceType.Huawei
import ru.cardsmobile.hmstest.domain.repository.LocationRepository
import ru.cardsmobile.hmstest.domain.repository.LocationUpdateListener
import ru.cardsmobile.hmstest.extentions.mapFailure
import java.util.concurrent.ConcurrentHashMap
import kotlin.Exception

private typealias HmsLocationCallback = LocationCallback
private typealias HmsLocationAvailability = LocationAvailability
private typealias HmsLocationResult = LocationResult
private typealias HmsLocationSettingsStates = LocationSettingsStates
private typealias HmsLocationRequest = LocationRequest
private typealias HmsLocationSettingsRequest = LocationSettingsRequest
private typealias HmsLocationSettingsRequestBuilder = LocationSettingsRequest.Builder
private typealias GmsLocationCallback = com.google.android.gms.location.LocationCallback
private typealias GmsLocationAvailability = com.google.android.gms.location.LocationAvailability
private typealias GmsLocationResult = com.google.android.gms.location.LocationResult
private typealias GmsLocationSettingsStates = com.google.android.gms.location.LocationSettingsStates
private typealias GmsLocationRequest = com.google.android.gms.location.LocationRequest
private typealias GmsLocationSettingsRequest = com.google.android.gms.location.LocationSettingsRequest
private typealias GmsLocationSettingsRequestBuilder = com.google.android.gms.location.LocationSettingsRequest.Builder

class LocationRepositoryImpl(
    private val hmsLocationDataSource: HmsLocationDataSource,
    private val gmsLocationDataSource: GmsLocationDataSource
) : LocationRepository {

    private val updateListeners = ConcurrentHashMap<LocationUpdateListener, Any>()

    override fun getLastLocation(
        mobileServiceType: MobileServiceType,
        callback: (Result<Location?>) -> Unit
    ) {
        when (mobileServiceType) {
            Google -> gmsLocationDataSource
                .getLastLocation(callback)
            Huawei -> hmsLocationDataSource
                .getLastLocation(callback)
        }
    }

    override fun getLocationUpdates(
        mobileServiceType: MobileServiceType,
        params: LocationUpdateParams,
        updateListener: LocationUpdateListener,
        operationCallback: (Result<Unit>) -> Unit
    ) {
        when (mobileServiceType) {
            Google -> {
                val callback = updateListener.toGmsLocationCallback()
                gmsLocationDataSource
                    .getLocationUpdates(
                        locationRequest = params.toGmsLocationRequest(),
                        locationCallback = callback,
                        callback = { operationResult ->
                            operationResult.onSuccess {
                                registerLocationCallback(
                                    updateListener,
                                    callback
                                )
                            }
                            operationCallback(operationResult)
                        }
                    )
            }
            Huawei -> {
                val callback = updateListener.toHmsLocationCallback()
                hmsLocationDataSource.getLocationUpdates(
                    locationRequest = params.toHmsLocationRequest(),
                    locationCallback = callback,
                    callback = { operationResult ->
                        operationResult.onSuccess {
                            registerLocationCallback(
                                updateListener,
                                callback
                            )
                        }
                        operationCallback(operationResult)
                    }
                )
            }
        }
    }

    override fun removeLocationUpdates(
        mobileServiceType: MobileServiceType,
        updateListener: LocationUpdateListener,
        operationCallback: (Result<Unit>) -> Unit
    ) {
        val locationCallback = updateListeners[updateListener]
            ?: return operationCallback(Result.failure(Exception("Update listener not registered")))

        when (mobileServiceType) {
            Google -> gmsLocationDataSource.removeLocationUpdates(
                locationCallback = locationCallback as GmsLocationCallback,
                callback = { operationResult ->
                    operationResult.onSuccess { removeLocationCallback(updateListener) }
                    operationCallback(operationResult)
                }
            )
            Huawei -> hmsLocationDataSource.removeLocationUpdates(
                locationCallback = locationCallback as HmsLocationCallback,
                callback = { operationResult ->
                    operationResult.onSuccess { removeLocationCallback(updateListener) }
                    operationCallback(operationResult)
                }
            )
        }
    }

    override fun checkLocationSettings(
        mobileServiceType: MobileServiceType,
        settings: LocationSettings,
        callback: (Result<Unit>) -> Unit
    ) {
        when (mobileServiceType) {
            Google -> gmsLocationDataSource.checkLocationSettings(
                settings = settings.toGmsLocationSettingsRequest(),
                callback = { result ->
                    callback(
                        result.mapFailure { it.wrapGoogleSettingsException() }
                            .map { Unit }
                    )
                }
            )
            Huawei -> hmsLocationDataSource.checkLocationSettings(
                settings = settings.toHmsLocationSettingsRequest(),
                callback = { result ->
                    callback(
                        result.mapFailure { it.wrapHuaweiSettingsException() }
                            .map { Unit }
                    )
                }
            )
        }
    }

    private fun registerLocationCallback(updateListener: LocationUpdateListener, callback: Any) {
        updateListeners[updateListener] = callback
    }

    private fun removeLocationCallback(updateListener: LocationUpdateListener) {
        updateListeners.remove(updateListener)
    }

    private fun LocationUpdateListener.toHmsLocationCallback(): HmsLocationCallback =
        object : HmsLocationCallback() {

            override fun onLocationAvailability(availability: HmsLocationAvailability?) {
                availability?.let {
                    invoke(null, availability.toAvailabilityInfo())
                }
            }

            override fun onLocationResult(result: HmsLocationResult?) {
                result?.let {
                    invoke(it.toLocationInfo(), null)
                }
            }
        }

    private fun LocationUpdateListener.toGmsLocationCallback(): GmsLocationCallback =
        object : GmsLocationCallback() {

            override fun onLocationAvailability(availability: GmsLocationAvailability?) {
                availability?.let {
                    invoke(null, availability.toAvailabilityInfo())
                }
            }

            override fun onLocationResult(result: GmsLocationResult?) {
                result?.let {
                    invoke(it.toLocationInfo(), null)
                }
            }
        }

    private fun LocationSettings.toHmsLocationSettingsRequest(): HmsLocationSettingsRequest =
        HmsLocationSettingsRequestBuilder()
            .addAllLocationRequests(locationRequests.map { it.toHmsLocationRequest() })
            .setAlwaysShow(isAlwaysShow)
            .build()

    private fun LocationUpdateParams.toHmsLocationRequest(): HmsLocationRequest =
        HmsLocationRequest()
            .setInterval(interval)
            .setPriority(priority.toGmsPriority())

    private fun LocationSettings.toGmsLocationSettingsRequest(): GmsLocationSettingsRequest =
        GmsLocationSettingsRequestBuilder()
            .addAllLocationRequests(locationRequests.map { it.toGmsLocationRequest() })
            .setAlwaysShow(isAlwaysShow)
            .build()

    private fun LocationUpdateParams.toGmsLocationRequest(): GmsLocationRequest =
        GmsLocationRequest()
            .setInterval(interval)
            .setPriority(priority.toHmsPriority())

    private fun HmsLocationAvailability.toAvailabilityInfo(): AvailabilityInfo =
        AvailabilityInfo(isLocationAvailable)

    private fun HmsLocationResult.toLocationInfo(): LocationInfo =
        LocationInfo(lastLocation.longitude, lastLocation.latitude)

    private fun Priority.toHmsPriority(): Int = when (this) {
        Priority.PRIORITY_HIGH_ACCURACY -> HmsLocationRequest.PRIORITY_HIGH_ACCURACY
        Priority.PRIORITY_BALANCED_POWER_ACCURACY -> HmsLocationRequest.PRIORITY_HIGH_ACCURACY
        Priority.PRIORITY_LOW_POWER -> HmsLocationRequest.PRIORITY_LOW_POWER
        Priority.PRIORITY_NO_POWER -> HmsLocationRequest.PRIORITY_NO_POWER
        Priority.PRIORITY_HD_ACCURACY -> HmsLocationRequest.PRIORITY_HD_ACCURACY
        Priority.PRIORITY_INDOOR -> HmsLocationRequest.PRIORITY_INDOOR
    }

    private fun GmsLocationAvailability.toAvailabilityInfo(): AvailabilityInfo =
        AvailabilityInfo(isLocationAvailable)

    private fun GmsLocationResult.toLocationInfo(): LocationInfo =
        LocationInfo(lastLocation.longitude, lastLocation.latitude)

    private fun Priority.toGmsPriority(): Int = when (this) {
        Priority.PRIORITY_HIGH_ACCURACY -> GmsLocationRequest.PRIORITY_HIGH_ACCURACY
        Priority.PRIORITY_BALANCED_POWER_ACCURACY -> GmsLocationRequest.PRIORITY_HIGH_ACCURACY
        Priority.PRIORITY_LOW_POWER -> GmsLocationRequest.PRIORITY_LOW_POWER
        Priority.PRIORITY_NO_POWER -> GmsLocationRequest.PRIORITY_NO_POWER

        Priority.PRIORITY_HD_ACCURACY,
        Priority.PRIORITY_INDOOR -> throw IllegalStateException()
    }

    private fun Throwable.wrapHuaweiSettingsException() = if (this is HmsResolvableApiException) {
        SettingException.HuaweiSettingException(this)
    } else this

    private fun Throwable.wrapGoogleSettingsException() = if (this is GmsResolvableApiException) {
        SettingException.GoogleSettingException(this)
    } else this
}

