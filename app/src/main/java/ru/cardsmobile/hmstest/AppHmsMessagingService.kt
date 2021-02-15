package ru.cardsmobile.hmstest

import android.util.Log
import com.huawei.hms.push.HmsMessageService
import com.huawei.hms.push.RemoteMessage
import ru.cardsmobile.hmstest.di.Di
import ru.cardsmobile.hmstest.domain.entity.MobileServiceType
import ru.cardsmobile.hmstest.domain.usecase.OnPushTokenUpdated

class AppHmsMessagingService : HmsMessageService() {

    private val onPushTokenUpdated: OnPushTokenUpdated = Di.onPushTokenUpdated

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)
        Log.d(LOG_TAG, "onMessageReceived remoteMessage.data=${remoteMessage?.dataOfMap}")
    }

    override fun onNewToken(token: String?) {
        super.onNewToken(token)
        Log.d(LOG_TAG, "onNewToken: token=$token")
        if (!token.isNullOrEmpty()) {
            onPushTokenUpdated(token, MobileServiceType.Huawei)
                .subscribe(
                    { /* Do nothing */ },
                    { Log.e(LOG_TAG, "Error deliver updated token", it) }
                )
        }
    }

    private companion object {
        const val LOG_TAG = "AppHmsMessagingService"
    }
}
