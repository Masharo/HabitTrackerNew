package com.masharo.habits.dataNew.repository

import androidx.work.*
import com.masharo.habits.dataNew.domainToRemoteHabit
import com.masharo.habits.dataNew.remote.HabitServiceImpl
import com.masharo.habits.dataNew.remote.worker.AddHabitWorker
import com.masharo.habits.dataNew.remote.worker.SetHabitWorker
import com.masharo.habits.dataNew.remote.worker.UpdateAllHabitWorker
import com.masharo.habits.domain.model.Habit
import com.masharo.habits.domain.model.Id
import com.masharo.habits.domain.repository.RemoteHabitRepository

class RemoteHabitRepositoryImpl(
    private val service: HabitServiceImpl
): RemoteHabitRepository {

    override suspend fun incGoneCountHabitRemote(id: Id) {
        TODO("Not yet implemented")
    }

    override suspend fun putHabitRemote(habit: Habit) {

    }

    override suspend fun deleteHabitRemote(id: Id) {
        TODO("Not yet implemented")
    }

    override suspend fun updateHabits() {

    }

}