package com.masharo.habits.di.dagger

import androidx.fragment.app.Fragment
import dagger.Component

@Component(modules = [AppModule::class, DomainModule::class, DataModule::class])
interface AppComponent {
    fun inject(fragment: Fragment)
}