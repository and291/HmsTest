package com.example.simple_examples.data.source

import android.annotation.SuppressLint
import android.util.Log

class EmuiDataSource {

    @SuppressLint("PrivateApi")
    fun getEmuiApiLevel(): Int? = try {
        val clazz = Class.forName("android.os.SystemProperties")
        val get = clazz.getMethod("getInt", String::class.java, Int::class.java)
        val currentApiLevel = get.invoke(clazz, "ro.build.hw_emui_api_level", UNKNOWN_API_LEVEL) as Int
        currentApiLevel.takeIf { it != UNKNOWN_API_LEVEL }
    } catch (e: Exception) {
        Log.e(LOG_TAG, "Unable to get EMUI api level", e)
        null
    }

    companion object {
        private const val LOG_TAG = "EmuiDataSource"

        private const val UNKNOWN_API_LEVEL = -1
    }
}
