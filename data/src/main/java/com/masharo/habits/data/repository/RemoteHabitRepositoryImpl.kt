package com.masharo.habits.data.repository

import com.masharo.habits.data.domainHabitToParamPutRemote
import com.masharo.habits.data.domainToDataId
import com.masharo.habits.data.remote.HabitService
import com.masharo.habits.domain.model.Habit
import com.masharo.habits.domain.model.Id
import com.masharo.habits.domain.repository.RemoteHabitRepository

class RemoteHabitRepositoryImpl(
    private val service: HabitService
): RemoteHabitRepository {

    override suspend fun incGoneCountHabitRemote(id: Id) {
        service.incGoneCountHabitRemote(domainToDataId(id))
    }

    override suspend fun putHabitRemote(habit: Habit) {
        service.putHabitRemote(
            domainHabitToParamPutRemote(habit)
        )
    }

    override suspend fun deleteHabitRemote(id: Id) {
        service.deleteHabitRemote(domainToDataId(id))
    }

    override suspend fun updateHabits() {
        service.updateHabits()
    }

}