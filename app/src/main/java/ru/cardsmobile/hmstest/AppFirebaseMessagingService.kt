package ru.cardsmobile.hmstest

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class AppFirebaseMessagingService : FirebaseMessagingService() {

    @SuppressLint("LongLogTag")
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d(LOG_TAG, "onMessageReceived remoteMessage=$remoteMessage")
    }

    @SuppressLint("LongLogTag")
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(LOG_TAG, "onNewToken: token=$token")
    }

    private companion object {
        const val LOG_TAG = "AppFirebaseMessagingService"
    }
}
