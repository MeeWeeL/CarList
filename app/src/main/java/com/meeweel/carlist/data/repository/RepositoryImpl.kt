package com.meeweel.carlist.data.repository

import com.meeweel.carlist.data.local.LocalDataSource
import com.meeweel.carlist.data.remote.RemoteDataSource
import com.meeweel.carlist.domain.CarModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class RepositoryImpl(
    private val remoteDB: RemoteDataSource,
    private val localDB: LocalDataSource
) : Repository {

    override fun getCarList(): Single<List<CarModel>> {
        return localDB.getCarList()
    }

    override fun getCarById(carId: Int): Single<CarModel> {
        return localDB.getCarById(carId)
    }

    override fun addNewCarData(newCarData: CarModel): Completable {
        return localDB.addNewCarData(newCarData)
    }

    override fun addNewCarData(newCarData: List<CarModel>) {
        localDB.addNewCarData(newCarData)
    }

    override fun getRemoteData(): Single<List<CarModel>> {
        return remoteDB.getCarList().map {
            localDB.addNewCarData(it)
            return@map it
        }
    }
}