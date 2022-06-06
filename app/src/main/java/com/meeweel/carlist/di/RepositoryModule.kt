package com.meeweel.carlist.di

import android.content.Context
import androidx.room.Room
import com.meeweel.carlist.data.local.LocalDataSource
import com.meeweel.carlist.data.local.LocalDataSourceImpl
import com.meeweel.carlist.data.local.room.CarEntityDao
import com.meeweel.carlist.data.local.room.CarEntityDataBase
import com.meeweel.carlist.data.remote.RemoteDataSource
import com.meeweel.carlist.data.remote.RemoteDataSourceImpl
import com.meeweel.carlist.data.remote.retrofit.CarsApi
import com.meeweel.carlist.data.remote.retrofit.RetrofitImpl
import com.meeweel.carlist.data.repository.Repository
import com.meeweel.carlist.data.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    internal fun provideRepository(
        dataSourceLocal: LocalDataSource,
        dataSourceRemote: RemoteDataSource
    ):
            Repository = RepositoryImpl(dataSourceRemote, dataSourceLocal)

    @Provides
    @Singleton
    internal fun provideRepositoryLocal(dataSourceLocal: CarEntityDao):
            LocalDataSource = LocalDataSourceImpl(dataSourceLocal)

    @Provides
    @Singleton
    internal fun provideRepositoryRemote(dataSourceRemote: CarsApi):
            RemoteDataSource = RemoteDataSourceImpl(dataSourceRemote)

    @Provides
    @Singleton
    internal fun provideDataSourceLocal(context: Context): CarEntityDao =
        Room.databaseBuilder(context, CarEntityDataBase::class.java, DB_NAME)
            .allowMainThreadQueries().build().entityDao()


    @Provides
    @Singleton
    internal fun provideDataSourceRemote(): CarsApi = RetrofitImpl().getService()

    companion object {
        const val DB_NAME = "CarEntity.db"
    }
}