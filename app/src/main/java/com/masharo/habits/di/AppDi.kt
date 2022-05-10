package com.masharo.habits.di

import com.masharo.habits.presentation.habit.HabitViewModel
import com.masharo.habits.presentation.listHabit.HabitListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { params ->
        HabitViewModel(
            context = get(),
            repository = get(),
            habitId = params.getOrNull()
        )
    }

    viewModel {
        HabitListViewModel(
            context = get(),
            repository = get()
        )
    }

}