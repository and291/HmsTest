package ru.cardsmobile.hmstest.presentation.location

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.cardsmobile.hmstest.di.Di
import ru.cardsmobile.hmstest.domain.entity.AvailabilityInfo
import ru.cardsmobile.hmstest.domain.entity.LocationInfo
import ru.cardsmobile.hmstest.domain.repository.LocationUpdateListener

class LocationViewModel : ViewModel() {

    private val getLastLocation = Di.getLastLocation
    private val getLocationUpdates = Di.getLocationUpdates
    private val removeLocationUpdates = Di.removeLocationUpdates
    private val checkLocationSettings = Di.checkLocationSettings
    private val updateListener = object : LocationUpdateListener {

        override fun invoke(p1: LocationInfo?, p2: AvailabilityInfo?) {
            additionalText.postValue(p1 to p2)
        }
    }

    val operationText = MutableLiveData<Result<String>>()
    val additionalText = MutableLiveData<Pair<LocationInfo?, AvailabilityInfo?>>()

    fun getLastLocation() {
        getLastLocation.invoke { result ->
            operationText.postValue(result.map { it.toString() })
        }
    }

    fun getLocationUpdates() {
        getLocationUpdates.invoke(
            updateListener,
            { result -> operationText.postValue(result.map { it.toString() }) }
        )
    }

    fun removeLocationUpdates() {
        removeLocationUpdates.invoke(
            updateListener,
            { result -> operationText.postValue(result.map { it.toString() }) }
        )
    }

    fun checkLocationSettings() {
        checkLocationSettings.invoke { result ->
            operationText.postValue(result.map { it.toString() })
        }
    }
}