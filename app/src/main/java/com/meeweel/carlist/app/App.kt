package com.meeweel.carlist.app

import android.app.Application
import com.meeweel.carlist.di.AppComponent
import com.meeweel.carlist.di.DaggerAppComponent

class App : Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        appInstance = this
        component = DaggerAppComponent.builder()
            .setContext(this)
            .build()
    }

    companion object {
        lateinit var appInstance: App
    }
}