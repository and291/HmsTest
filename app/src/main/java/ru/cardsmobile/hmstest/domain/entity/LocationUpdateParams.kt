package ru.cardsmobile.hmstest.domain.entity

data class LocationUpdateParams(
    val interval: Long,
    val priority: Priority
)

enum class Priority {
    PRIORITY_HIGH_ACCURACY,
    PRIORITY_BALANCED_POWER_ACCURACY,
    PRIORITY_LOW_POWER,
    PRIORITY_NO_POWER,

    PRIORITY_HD_ACCURACY,
    PRIORITY_INDOOR,
}