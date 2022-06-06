package com.meeweel.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RoomCarEntity::class], version = 1, exportSchema = false)
abstract class CarEntityDataBase : RoomDatabase() {

    abstract fun entityDao(): CarEntityDao
}