package com.masharo.habits.domain.usecase

import com.masharo.habits.domain.DBHabitRepository
import com.masharo.habits.domain.model.Habit
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GetAllHabitsUseCase(private val dbRepository: DBHabitRepository,
                          private val dispatcher: CoroutineDispatcher
) {

    suspend fun execute(): Flow<List<Habit>> =
        withContext(dispatcher) {
            dbRepository.getAllHabits()
        }

}