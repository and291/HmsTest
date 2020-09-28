package ru.cardsmobile.hmstest.presentation

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import android.util.Log
import ru.cardsmobile.hmstest.di.Di

class HmsTestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Skip initialization if this is :pushservice process
        if (isPushServiceProcess()) return

        Di.application = this
    }

    private fun isPushServiceProcess(): Boolean {
        val detectedProcessName: String? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getProcessName()
        } else {
            getProcessNameForLowerApi()
        }
        Log.d(LOG_TAG, "detectedProcessName=$detectedProcessName")
        return detectedProcessName == "$packageName:pushservice"
    }

    @SuppressLint("PrivateApi", "DiscouragedPrivateApi")
    private fun getProcessNameForLowerApi() = try {
        val activityThread = Class.forName("android.app.ActivityThread")
        val getProcessName = activityThread.getDeclaredMethod("currentProcessName")
        getProcessName.invoke(null) as String
    } catch (e: Exception) {
        null
    }

    private companion object {
        const val LOG_TAG = "HmsTestApplication"
    }
}
