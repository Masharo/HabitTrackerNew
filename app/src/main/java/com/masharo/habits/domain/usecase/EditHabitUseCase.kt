package com.masharo.habits.domain.usecase

import com.masharo.habits.domain.DBHabitRepository
import com.masharo.habits.domain.RemoteHabitRepository
import com.masharo.habits.domain.model.Habit
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class EditHabitUseCase(private val dbRepository: DBHabitRepository,
                       private val remoteRepository: RemoteHabitRepository,
                       private val dispatcher: CoroutineDispatcher
) {

    suspend fun execute(habit: Habit): Boolean =
        withContext(dispatcher) {
            try {

                dbRepository.editHabit(habit)
                remoteRepository.putHabitRemote(habit)

                return@withContext true
            } catch (ex: Exception) {
                return@withContext false
            }
        }

}