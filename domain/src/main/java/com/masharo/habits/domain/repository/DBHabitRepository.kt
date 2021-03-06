package com.masharo.habits.domain.repository

import com.masharo.habits.domain.model.Habit
import com.masharo.habits.domain.model.Id
import kotlinx.coroutines.flow.Flow

interface DBHabitRepository {

    suspend fun editHabit(habit: Habit)
    suspend fun addHabit(habit: Habit): Long
    suspend fun deleteHabit(id: Id)
    suspend fun getHabit(id: Id): Habit?
    fun getAllHabits(): Flow<List<Habit>>

}