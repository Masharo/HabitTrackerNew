package com.masharo.habits.domain.usecase

import com.masharo.habits.domain.model.Id
import com.masharo.habits.domain.repository.DBHabitRepository
import com.masharo.habits.domain.repository.RemoteHabitRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteHabitUseCase(private val dbRepository: DBHabitRepository,
                         private val remoteRepository: RemoteHabitRepository,
                         private val dispatcher: CoroutineDispatcher
) {

    suspend fun execute(id: Id): Boolean =
        withContext(dispatcher) {
            try {

                withContext(Dispatchers.Default) {
                    id.idRemote = dbRepository.getHabit(id)?.idRemote
                }

                remoteRepository.deleteHabitRemote(id)
                dbRepository.deleteHabit(id)

                return@withContext true
            } catch (ex: Exception) {
                return@withContext false
            }
        }

}