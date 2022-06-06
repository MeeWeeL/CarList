package com.meeweel.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface CarEntityDao {

    @Query("SELECT * FROM RoomCarEntity")
    fun getCarList() : Single<List<RoomCarEntity>>

    @Query("SELECT * FROM RoomCarEntity WHERE id = :carId")
    fun getCarByCarId(carId: Int) : Single<RoomCarEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entityList: List<RoomCarEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: RoomCarEntity) : Completable
}