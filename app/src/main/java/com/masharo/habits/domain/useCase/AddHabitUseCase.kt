package com.masharo.habits.domain.useCase

import com.masharo.habits.domain.HabitRepository
import com.masharo.habits.domain.model.Habit
import com.masharo.habits.domain.model.output.OutputDataAdd
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class AddHabitUseCase(private val repository: HabitRepository,
                      private val dispatcher: CoroutineDispatcher
) {

    suspend fun execute(habit: Habit): OutputDataAdd =
        return withContext(dispatcher) {

        }

}