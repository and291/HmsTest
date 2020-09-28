package ru.cardsmobile.hmstest.presentation.security

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.cardsmobile.hmstest.di.Di

class SecurityViewModel : ViewModel() {

    private val getSecurityCheckResult = Di.getSecurityCheckResult
    private val compositeDisposable = CompositeDisposable()
    private val _text = MutableLiveData<Result<String>>()

    val text: LiveData<Result<String>> = _text

    init {
        val subscription = getSecurityCheckResult()
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
