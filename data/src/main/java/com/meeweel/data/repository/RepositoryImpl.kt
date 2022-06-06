package com.meeweel.data.repository

import com.meeweel.core.domain.CarModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class RepositoryImpl(
    private val remoteDB: com.meeweel.data.remote.RemoteDataSource,
    private val localDB: com.meeweel.data.local.LocalDataSource
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