package com.masharo.habits.data

import androidx.lifecycle.LiveData
import com.masharo.habits.data.HabitDatabase
import com.masharo.habits.data.HabitRepository
import com.masharo.habits.data.model.Habit

class HabitRepositoryImpl(val db: HabitDatabase): HabitRepository {

    override fun getHabits(): LiveData<List<Habit>> {
        return db.getHabitDao().habits()
    }

    override suspend fun setHabit(habit: Habit) {
        db.getHabitDao().set(habit)
    }

    override suspend fun getHabit(id: Int): Habit? {
        return db.getHabitDao().get(id)
    }

    override suspend fun addHabit(habit: Habit) {
        db.getHabitDao().add(habit)
    }

}