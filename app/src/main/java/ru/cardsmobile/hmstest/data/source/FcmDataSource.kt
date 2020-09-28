package ru.cardsmobile.hmstest.data.source

import com.google.firebase.iid.FirebaseInstanceId
import io.reactivex.rxjava3.core.Single

class FcmDataSource(
    private val firebaseInstanceId: FirebaseInstanceId
) {

    fun getFcmPushToken(): Single<String> = Single
        .create { emitter ->
            firebaseInstanceId.instanceId
                .addOnSuccessListener {
                    if (!emitter.isDisposed) {
                        emitter.onSuccess(it.token)
                    }
                }
                .addOnFailureListener {
                    emitter.tryOnError(it)
                }
        }
}
