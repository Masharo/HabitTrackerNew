package com.masharo.habits.domain.usecase

import com.masharo.habits.domain.DBHabitRepository
import com.masharo.habits.domain.RemoteHabitRepository
import com.masharo.habits.domain.model.Id
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.lang.Exception

class IncGoneCountHabitUseCase(private val dbRepository: DBHabitRepository,
                               private val remoteRepository: RemoteHabitRepository,
                               private val dispatcher: CoroutineDispatcher
) {

    suspend fun execute(id: Id): Boolean =
        withContext(dispatcher) {

            try {
                val habit = dbRepository.getHabit(id)
                habit?.apply {
                    countDone++
                    dbRepository.editHabit(habit)
                    remoteRepository.incGoneCountHabitRemote(id)
                } ?: return@withContext false

                return@withContext true
            } catch (ex: Exception) {
                return@withContext false
            }

        }

}