package com.masharo.habits.domain.usecase

import com.masharo.habits.domain.DBHabitRepository
import com.masharo.habits.domain.RemoteHabitRepository
import com.masharo.habits.domain.model.Id
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DeleteHabitUseCase(private val dbRepository: DBHabitRepository,
                         private val remoteRepository: RemoteHabitRepository,
                         private val dispatcher: CoroutineDispatcher
) {

    suspend fun execute(id: Id): Boolean =
        withContext(dispatcher) {
            try {

                dbRepository.deleteHabit(id)
                remoteRepository.deleteHabitRemote(id)

                return@withContext true
            } catch (ex: Exception) {
                return@withContext false
            }
        }

}