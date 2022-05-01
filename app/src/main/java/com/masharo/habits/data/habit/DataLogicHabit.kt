package com.masharo.habits.data.habit

interface DataLogicHabit {

    suspend fun setHabit(habit: Habit)

    suspend fun getHabit(id: Int): Habit?

    suspend fun addHabit(habit: Habit)

}