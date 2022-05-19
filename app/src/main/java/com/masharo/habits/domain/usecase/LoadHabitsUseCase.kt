package com.masharo.habits.domain.usecase

import com.masharo.habits.domain.RemoteHabitRepository
import com.masharo.habits.domain.model.Id
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class LoadHabitsUseCase(private val remoteRepository: RemoteHabitRepository,
                        private val dispatcher: CoroutineDispatcher
) {

    suspend fun execute(id: Id) {
        withContext(dispatcher) {
            remoteRepository.updateHabits()
        }
    }
}