package com.meeweel.data.remote

import com.meeweel.core.domain.CarModel
import com.meeweel.data.remote.retrofit.CarsApi
import com.meeweel.data.remote.retrofit.toCarModelList
import io.reactivex.rxjava3.core.Single

class RemoteDataSourceImpl(private val remoteDataSource: CarsApi) : RemoteDataSource {

    override fun getCarList(): Single<List<CarModel>> {
        return remoteDataSource.getCarList().map { it.toCarModelList() }
    }
}