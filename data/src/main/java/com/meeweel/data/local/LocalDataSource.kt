package com.meeweel.data.local

import com.meeweel.core.domain.CarModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface LocalDataSource {

    fun getCarList() : Single<List<CarModel>>
    fun getCarById(carId: Int) : Single<CarModel>
    fun addNewCarData(newCar: CarModel) : Completable
    fun addNewCarData(newCarData: List<CarModel>)
}