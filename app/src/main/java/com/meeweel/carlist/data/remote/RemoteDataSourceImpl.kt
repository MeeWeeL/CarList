package com.meeweel.carlist.data.remote

import com.meeweel.carlist.data.remote.retrofit.CarsApi
import com.meeweel.carlist.data.remote.retrofit.toCarModelList
import com.meeweel.carlist.domain.CarModel
import io.reactivex.rxjava3.core.Single

class RemoteDataSourceImpl(private val remoteDataSource: CarsApi) : RemoteDataSource {

    override fun getCarList(): Single<List<CarModel>> {
        return remoteDataSource.getCarList().map { it.toCarModelList() }
    }
}