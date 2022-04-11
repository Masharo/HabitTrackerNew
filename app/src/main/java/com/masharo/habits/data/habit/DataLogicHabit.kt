package com.masharo.habits.data.habit

import com.masharo.habits.Habit

interface DataLogicHabit {

    fun setHabit(habit: Habit)

    fun getHabit(id: Int?): Habit?

    fun addHabit(habit: Habit)

}