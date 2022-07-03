package com.masharo.habits.presentation.habit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.masharo.habits.domain.usecase.*
import javax.inject.Inject

//class HabitViewModelFactory @Inject constructor(
//    private val getHabitUseCase: GetHabitUseCase,
//    private val addHabitUseCase: AddHabitUseCase,
//    private val editHabitUseCase: EditHabitUseCase
//) : ViewModelProvider.Factory {
//
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return HabitViewModel(
//            getHabitUseCase = getHabitUseCase,
//            addHabitUseCase = addHabitUseCase,
//            editHabitUseCase = editHabitUseCase
//        ) as T
//    }
//}