package com.masharo.habits.domain

import com.masharo.habits.domain.model.Habit
import com.masharo.habits.domain.model.Id

interface RemoteHabitRepository {

    suspend fun incGoneCountHabitRemote(id: Id)
    suspend fun putHabitRemote(habit: Habit)
    suspend fun deleteHabitRemote(id: Id)
    suspend fun updateHabits()

}