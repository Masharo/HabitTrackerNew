package com.masharo.habits.data.habit

import com.masharo.habits.data.HabitDatabase

class RoomHabitDataLogic(val db: HabitDatabase): DataLogicHabit {

    override suspend fun setHabit(habit: Habit) {
        db.getHabitDao().set(habit)
    }

    override suspend fun getHabit(id: Int?): Habit? {
        return id?.let {
            db.getHabitDao().get(id)
        }
    }

    override suspend fun addHabit(habit: Habit) {
        db.getHabitDao().add(habit)
    }
}