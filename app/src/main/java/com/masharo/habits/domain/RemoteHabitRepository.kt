package com.masharo.habits.domain

import com.masharo.habits.domain.model.Habit
import com.masharo.habits.domain.model.Id

interface RemoteHabitRepository {

    fun incGoneCountHabitRemote(id: Id)
    fun putHabitRemote(habit: Habit)
    fun deleteHabitRemote(id: Id)
    fun updateHabits()

}