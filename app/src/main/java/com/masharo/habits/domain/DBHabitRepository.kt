package com.masharo.habits.domain

import com.masharo.habits.domain.model.Habit
import com.masharo.habits.domain.model.Id
import kotlinx.coroutines.flow.Flow

interface DBHabitRepository {

    fun editHabit(habit: Habit)
    fun addHabit(habit: Habit)
    fun deleteHabit(id: Id)
    fun getHabit(id: Id): Habit?
    fun getAllHabits(): Flow<List<Habit>>

}