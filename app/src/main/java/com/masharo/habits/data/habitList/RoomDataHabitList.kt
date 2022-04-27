package com.masharo.habits.data.habitList

import androidx.lifecycle.LiveData
import com.masharo.habits.data.HabitDatabase
import com.masharo.habits.data.habit.Habit

class RoomDataHabitList(val db: HabitDatabase): DataLogicHabitList {

    override suspend fun getHabits(): LiveData<List<Habit>> {
            return db.getHabitDao().habits()
    }
}