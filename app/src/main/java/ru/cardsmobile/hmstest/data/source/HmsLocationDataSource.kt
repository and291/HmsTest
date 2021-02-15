package ru.cardsmobile.hmstest.data.source

import android.location.Location
import android.os.Looper
import com.huawei.hms.location.*

class HmsLocationDataSource(
    private val locationProvider: Lazy<FusedLocationProviderClient>,
    private val locationSettings: Lazy<SettingsClient>
) {

    fun getLastLocation(callback: (Result<Location?>) -> Unit) {
        locationProvider.value.lastLocation
            .addOnSuccessListener { location -> callback(Result.success(location)) }
            .addOnFailureListener { e -> callback(Result.failure(e)) }
    }

    fun getLocationUpdates(
        locationRequest: LocationRequest,
        locationCallback: LocationCallback,
        callback: (Result<Unit>) -> Unit
    ) {
        locationProvider.value.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
            .addOnSuccessListener { callback(Result.success(Unit)) }
            .addOnFailureListener { e -> callback(Result.failure(e)) }
    }

    fun removeLocationUpdates(
        locationCallback: LocationCallback,
        callback: (Result<Unit>) -> Unit
    ) {
        locationProvider.value.removeLocationUpdates(locationCallback)
            .addOnSuccessListener { callback(Result.success(Unit)) }
            .addOnFailureListener { e -> callback(Result.failure(e)) }
    }

    fun checkLocationSettings(
        settings: LocationSettingsRequest,
        callback: (Result<LocationSettingsStates>) -> Unit
    ) {
        locationSettings.value.checkLocationSettings(settings)
            .addOnSuccessListener { response -> callback(Result.success(response.locationSettingsStates)) }
            .addOnFailureListener { e -> callback(Result.failure(e)) }
    }
}
