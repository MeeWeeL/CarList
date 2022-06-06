package com.meeweel.data.repository

import com.meeweel.core.domain.CarModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface Repository {

    fun getCarList() : Single<List<CarModel>>
    fun getCarById(carId: Int) : Single<CarModel>
    fun addNewCarData(newCarData: CarModel) : Completable
    fun addNewCarData(newCarData: List<CarModel>)
    fun getRemoteData() : Single<List<CarModel>>
}