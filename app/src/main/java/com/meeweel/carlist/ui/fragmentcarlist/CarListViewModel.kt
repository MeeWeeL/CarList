package com.meeweel.carlist.ui.fragmentcarlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meeweel.carlist.data.repository.FakeRepositoryImpl
import com.meeweel.carlist.data.repository.Repository
import com.meeweel.carlist.domain.CarListState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class CarListViewModel(private val repository: Repository = FakeRepositoryImpl()) : ViewModel() {

    private val liveDataToObserve: MutableLiveData<CarListState> = MutableLiveData()

    fun getData(): LiveData<CarListState> {
        return liveDataToObserve
    }

    fun getCarList() = getDataFromRepository()

    private fun getDataFromRepository() {
        repository.getCarList()
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                liveDataToObserve.postValue(CarListState.Loading)
            }
            .subscribe({
                liveDataToObserve.postValue(CarListState.Success(it))
            },{
                liveDataToObserve.postValue(CarListState.Error(it))
            })
    }
}