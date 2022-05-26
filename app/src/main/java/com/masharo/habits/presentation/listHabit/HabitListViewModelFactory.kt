package com.masharo.habits.presentation.listHabit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.masharo.habits.domain.usecase.GetAllHabitsUseCase
import com.masharo.habits.domain.usecase.IncDoneCountHabitUseCase
import com.masharo.habits.domain.usecase.LoadHabitsUseCase
import javax.inject.Inject

class HabitListViewModelFactory @Inject constructor(
    val loadHabitsUseCase: LoadHabitsUseCase,
    val getAllHabitsUseCase: GetAllHabitsUseCase,
    val incDoneCountHabitUseCase: IncDoneCountHabitUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HabitListViewModel(
            loadHabitsUseCase = loadHabitsUseCase,
            getAllHabitsUseCase = getAllHabitsUseCase,
            incDoneCountHabitUseCase = incDoneCountHabitUseCase
        ) as T
    }
}