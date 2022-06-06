package com.meeweel.carlist.di

import android.content.Context
import com.meeweel.carlist.ui.fragmentcardetails.CarDetailsViewModel
import com.meeweel.carlist.ui.fragmentcarlist.CarListViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RepositoryModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun setContext(context: Context): Builder

        fun build(): AppComponent
    }

    fun inject(fragment: CarListViewModel)
    fun inject(fragment: CarDetailsViewModel)
}