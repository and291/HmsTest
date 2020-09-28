package ru.cardsmobile.hmstest.data.source.availability

import android.annotation.SuppressLint
import android.util.Log
import io.reactivex.rxjava3.core.Maybe

class EmuiDataSource {

    @SuppressLint("PrivateApi")
    fun getEmuiApiLevel(): Maybe<Int> = Maybe
        .fromCallable<Int> {
            val clazz = Class.forName("android.os.SystemProperties")
            val get = clazz.getMethod("getInt", String::class.java, Int::class.java)
            val currentApiLevel = get.invoke(clazz, EMUI_API, UNKNOWN_API_LEVEL) as Int
            currentApiLevel.takeIf { it != UNKNOWN_API_LEVEL }
        }
        .doOnError { Log.e(LOG_TAG, "Unable to get EMUI api level", it) }
        .onErrorComplete()

    private companion object {
        const val LOG_TAG = "EmuiDataSource"

        const val EMUI_API = "ro.build.hw_emui_api_level"
        const val UNKNOWN_API_LEVEL = -1
    }
}
