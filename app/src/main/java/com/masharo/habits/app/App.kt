package com.masharo.habits.app

import android.app.Application
import com.masharo.habits.di.appModule
import com.masharo.habits.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            logger(AndroidLogger(Level.INFO))
            modules(listOf(appModule, dataModule))
        }
    }
}