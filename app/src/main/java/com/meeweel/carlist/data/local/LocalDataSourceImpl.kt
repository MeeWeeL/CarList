package com.meeweel.carlist.data.local

import androidx.room.Room
import com.meeweel.carlist.app.App.ContextHolder.context
import com.meeweel.carlist.data.local.room.*
import com.meeweel.carlist.domain.CarModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class LocalDataSourceImpl(
    private val dataBase: CarEntityDao = Room.databaseBuilder(
        context,
        CarEntityDataBase::class.java,
        DB_NAME
    ).allowMainThreadQueries().build().entityDao()
) : LocalDataSource {

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

    companion object {
        const val DB_NAME = "CarEntity.db"
    }
}