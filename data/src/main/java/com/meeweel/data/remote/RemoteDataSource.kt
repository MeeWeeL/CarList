package com.meeweel.data.remote

import com.meeweel.core.domain.CarModel
import io.reactivex.rxjava3.core.Single

interface RemoteDataSource {

    fun getCarList() : Single<List<CarModel>>
}