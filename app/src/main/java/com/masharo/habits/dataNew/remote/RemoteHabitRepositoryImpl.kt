package com.masharo.habits.dataNew.remote

import com.masharo.habits.dataNew.domainToDataHabit
import com.masharo.habits.domain.RemoteHabitRepository
import com.masharo.habits.domain.model.Habit
import com.masharo.habits.domain.model.Id

class RemoteHabitRepositoryImpl(
    private val api: HabitApi
): RemoteHabitRepository {
    override fun incGoneCountHabitRemote(id: Id) {
        TODO("Not yet implemented")
    }

    override fun putHabitRemote(habit: Habit) {
        domainToDataHabit(habit)
    }

    override fun deleteHabitRemote(id: Id) {
        TODO("Not yet implemented")
    }

    override fun updateHabits() {
        TODO("Not yet implemented")
    }

}