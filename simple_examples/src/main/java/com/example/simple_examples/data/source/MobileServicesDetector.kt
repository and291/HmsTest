package com.example.simple_examples.data.source

interface MobileServicesDetector {

    fun getAvailableServices(): Set<MobileServiceEnvironment>
}