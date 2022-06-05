package com.meeweel.carlist.ui.fragmentcarlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meeweel.carlist.data.repository.Repository
import com.meeweel.carlist.data.repository.RepositoryImpl
import com.meeweel.carlist.domain.CarBrand
import com.meeweel.carlist.domain.CarListState
import com.meeweel.carlist.domain.CarModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class CarListViewModel(private val repository: Repository = RepositoryImpl()) : ViewModel() {

    private var dataList: List<CarModel> = listOf()
    private val liveDataToObserve: MutableLiveData<CarListState> = MutableLiveData()
    private var sortStrategy = SortBy.BRAND_NAME
    private var brandFilter = CarBrand.ALL

    fun getData(): LiveData<CarListState> {
        return liveDataToObserve
    }

    fun getCarList() = getDataFromRepository()

    private fun getDataFromRepository() {
        repository.getCarList()
            .subscribeForCars()
    }

    private fun getRemoteData() {
        repository.getRemoteData()
            .subscribeForCars()
    }

    private fun Single<List<CarModel>>.subscribeForCars() {
        this.observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                liveDataToObserve.postValue(CarListState.Loading)
            }
            .subscribe({
                if (it.isEmpty()) {
                    getRemoteData()
                }
                else {
                    dataList = it
                    filterList()
                }
            }, {
                liveDataToObserve.postValue(CarListState.Error(it))
            })
    }

    enum class SortBy {
        BRAND_NAME, MODEL, COST_DOWN, COST_UP
    }

    fun sort(strategy: SortBy) {
        sortStrategy = strategy
        filterList()
    }

    fun filter(brand: CarBrand) {
        brandFilter = brand
        filterList()
    }

    private fun filterList() {
        val newList: MutableList<CarModel> = mutableListOf()
        for (item in dataList) {
            if (item.brand == brandFilter || brandFilter == CarBrand.ALL) newList.add(item)
        }
        when (sortStrategy) {
            SortBy.BRAND_NAME -> newList.sortBy { it.brand.brand }
            SortBy.MODEL -> newList.sortBy { it.model }
            SortBy.COST_DOWN -> newList.sortBy { it.cost }
            SortBy.COST_UP -> {
                newList.sortBy { it.cost }
                newList.reverse()
            }
        }
        liveDataToObserve.postValue(CarListState.Success(newList))
    }

    fun addNewCarData(newCarData: CarModel) {
        repository.addNewCarData(newCarData)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({
                getCarList()
            },{})
    }
}