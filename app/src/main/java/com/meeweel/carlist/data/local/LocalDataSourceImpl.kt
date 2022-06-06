package com.meeweel.carlist.data.local

import com.meeweel.carlist.data.local.room.*
import com.meeweel.carlist.domain.CarModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class LocalDataSourceImpl(private val dataBase: CarEntityDao) : LocalDataSource {

    override fun getCarList(): Single<List<CarModel>> {
        return dataBase.getCarList().map { it.toCarModelList() }
    }

    override fun getCarById(carId: Int): Single<CarModel> {
        return dataBase.getCarByCarId(carId).map { it.toCarModel() }
    }

    override fun addNewCarData(newCar: CarModel) : Completable {
        return dataBase.insert(newCar.toRoomCarEntity())
    }

    override fun addNewCarData(newCarData: List<CarModel>) {
        dataBase.insert(newCarData.toRoomCarEntityList())
    }
}