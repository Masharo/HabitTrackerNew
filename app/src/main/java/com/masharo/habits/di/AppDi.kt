package com.masharo.habits.di

import com.masharo.habits.presentation.habit.HabitViewModel
import com.masharo.habits.presentation.listHabit.HabitListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel<HabitViewModel>() { params ->
        HabitViewModel(context = get(), id = params.get())
    }

    viewModel<HabitListViewModel> {
        HabitListViewModel(context = get())
    }

}