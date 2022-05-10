package com.masharo.habits.data

import androidx.lifecycle.LiveData
import com.masharo.habits.data.model.Habit

interface HabitRepository {

    fun getHabits(): LiveData<List<Habit>>

    suspend fun setHabit(habit: Habit)

    suspend fun getHabit(id: Int): Habit?

    suspend fun addHabit(habit: Habit)

}