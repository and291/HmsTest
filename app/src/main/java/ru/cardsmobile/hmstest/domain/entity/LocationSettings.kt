package ru.cardsmobile.hmstest.domain.entity

data class LocationSettings(
    val locationRequests: List<LocationUpdateParams>,
    val isAlwaysShow: Boolean
)