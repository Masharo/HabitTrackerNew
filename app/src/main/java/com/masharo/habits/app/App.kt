package com.masharo.habits.app

import android.app.Application
import com.masharo.habits.di.dagger.AppComponent
import com.masharo.habits.di.dagger.AppModule
import com.masharo.habits.di.dagger.DaggerAppComponent

class App: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

//        startKoin {
//            androidContext(this@App)
//            logger(AndroidLogger(Level.INFO))
//            modules(listOf(appModule, dataModule))
//        }

        //dagger
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(context = applicationContext))
            .build()
    }
}