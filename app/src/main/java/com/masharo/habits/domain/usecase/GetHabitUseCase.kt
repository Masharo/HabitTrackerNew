package com.masharo.habits.domain.usecase

import com.masharo.habits.domain.repository.DBHabitRepository
import com.masharo.habits.domain.model.Habit
import com.masharo.habits.domain.model.Id
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetHabitUseCase(private val repository: DBHabitRepository,
                      private val dispatcher: CoroutineDispatcher
) {

    suspend fun execute(id: Id): Habit? =
        withContext(dispatcher) {
            repository.getHabit(id)
        }

}