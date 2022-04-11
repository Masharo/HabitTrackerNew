package com.masharo.habits.data.habit

interface DataLogicHabit {

    fun setHabit(habit: Habit)

    fun getHabit(id: Int?): Habit?

    fun addHabit(habit: Habit)

}