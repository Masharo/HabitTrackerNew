//package com.masharo.habits.di
//
//import com.masharo.habits.presentation.habit.HabitViewModel
//import com.masharo.habits.presentation.listHabit.HabitListViewModel
//import org.koin.androidx.viewmodel.dsl.viewModel
//import org.koin.dsl.module
//
//val appModule = module {
//
//    viewModel { params ->
//        HabitViewModel(
//            habitId = params.getOrNull(),
//            getHabitUseCase = get(),
//            addHabitUseCase = get(),
//            editHabitUseCase = get()
//        )
//    }
//
//    viewModel {
//        HabitListViewModel(
//            loadHabitsUseCase = get(),
//            getAllHabitsUseCase = get()
//        )
//    }
//
//}