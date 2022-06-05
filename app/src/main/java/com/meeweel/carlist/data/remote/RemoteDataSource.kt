package com.meeweel.carlist.data.remote

import com.meeweel.carlist.domain.CarModel
import io.reactivex.rxjava3.core.Single

interface RemoteDataSource {

    fun getCarList() : Single<List<CarModel>>
}