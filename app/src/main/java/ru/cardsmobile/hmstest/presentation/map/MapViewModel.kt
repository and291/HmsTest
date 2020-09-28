package ru.cardsmobile.hmstest.presentation.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.cardsmobile.hmstest.di.Di
import ru.cardsmobile.hmstest.domain.entity.MobileServiceType
import ru.cardsmobile.hmstest.domain.usecase.SelectMobileServiceType

class MapViewModel : ViewModel() {

    private val selectMobileServiceType = Di.selectMobileServiceType

    private val _mobileServiceType = MutableLiveData<Result<MobileServiceType>>()
    val mobileServiceType: LiveData<Result<MobileServiceType>> = _mobileServiceType

    init {
        selectMobileServiceType(SelectMobileServiceType.Case.Map)
            .toSingle()
            .subscribe({
                _mobileServiceType.postValue(Result.success(it))
            },{
                _mobileServiceType.postValue(Result.failure(it))
            })
    }
}
