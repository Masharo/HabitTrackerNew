package com.masharo.habits.data.habitList

import androidx.lifecycle.LiveData
import com.masharo.habits.data.habit.Habit

interface DataLogicHabitList {
    suspend fun getHabits(): LiveData<List<Habit>>
}