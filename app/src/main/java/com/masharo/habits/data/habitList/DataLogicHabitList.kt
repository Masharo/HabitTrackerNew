package com.masharo.habits.data.habitList

import androidx.lifecycle.LiveData
import com.masharo.habits.Habit

interface DataLogicHabitList {
    fun getHabits(): LiveData<List<Habit>>

    fun getHabitsTypeFilter(type: Habit.TypeHabit): LiveData<List<Habit>>
}