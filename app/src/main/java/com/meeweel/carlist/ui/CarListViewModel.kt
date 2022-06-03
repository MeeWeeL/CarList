package com.meeweel.carlist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meeweel.carlist.domain.CarListState

class CarListViewModel : ViewModel() {

    private val liveDataToObserve: MutableLiveData<CarListState> = MutableLiveData()

    fun getData(): LiveData<CarListState> {
        return liveDataToObserve
    }

    fun getCarList() = getDataFromRepository()

    private fun getDataFromRepository() {
//        TODO("Repository")
    }
}