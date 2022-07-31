package com.masharo.habits.di.dagger

import com.masharo.habits.presentation.habit.HabitFragment
import com.masharo.habits.presentation.listHabit.HabitListFragment
import com.masharo.habits.presentation.listHabit.SortAndSearchFragment
import dagger.Component

@Component(modules = [AppModule::class, DomainModule::class, DataModule::class])
interface AppComponent {
    fun inject(fragment: HabitListFragment)
    fun inject(fragment: SortAndSearchFragment)
    fun inject(fragment: HabitFragment)
}