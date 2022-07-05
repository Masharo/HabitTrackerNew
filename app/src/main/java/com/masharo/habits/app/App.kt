package com.masharo.habits.app

import android.app.Application
import android.content.Context
import com.masharo.habits.di.dagger.AppComponent
import com.masharo.habits.di.dagger.AppModule
import com.masharo.habits.di.dagger.DaggerAppComponent

class App: Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .appModule(AppModule(context = applicationContext))
            .build()
    }

}

val Context.appComponent: AppComponent
get() = when(this) {
    is App -> appComponent
    else -> applicationContext.appComponent
}