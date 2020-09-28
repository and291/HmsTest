package ru.cardsmobile.hmstest.presentation.pushtoken

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.cardsmobile.hmstest.di.Di

class PushTokenViewModel : ViewModel() {

    private val getPushToken = Di.getPushTokenWithTimeout
    private val compositeDisposable = CompositeDisposable()
    private val _text = MutableLiveData<Result<String>>()
    val text: LiveData<Result<String>> = _text

    init {
        val subscription = getPushToken()
            .toSingle()
            .subscribe({
                _text.postValue(Result.success(it))
            }, {
                _text.postValue(Result.failure(it))
            })
        compositeDisposable.add(subscription)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
