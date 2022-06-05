package com.meeweel.carlist.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object ContextHolder {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
}