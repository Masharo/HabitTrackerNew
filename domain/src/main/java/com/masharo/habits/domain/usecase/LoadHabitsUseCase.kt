package com.masharo.habits.domain.usecase

import com.masharo.habits.domain.repository.RemoteHabitRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class LoadHabitsUseCase(private val remoteRepository: RemoteHabitRepository,
                        private val dispatcher: CoroutineDispatcher
) {

    suspend fun execute() {
        withContext(dispatcher) {
            remoteRepository.updateHabits()
        }
    }
}